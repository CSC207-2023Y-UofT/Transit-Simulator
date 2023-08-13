package persistence.impl;

import entity.model.Direction;
import entity.model.control.TransitModel;
import entity.model.node.Node;
import entity.model.node.line.NodeLineProfile;
import entity.model.node.NodeTracker;
import entity.model.node.station.StationFactory;
import entity.model.train.track.TrackSegment;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.boundary.ModelDataStore;
import util.Preconditions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("BlockingMethodInNonBlockingContext")
public class JsonModelDataStore implements ModelDataStore {

    /**
     * The file to read the model from.
     */
    private final File file;

    /**
     * Creates a new JsonModelDataStore that reads from the specified file.
     *
     * @param file the file to read the model from
     */
    public JsonModelDataStore(File file) {
        this.file = file;
    }

    /**
     * Reads the model from a JSON file and creates the model.
     * Sets the static variables to hold a specific model.
     * <br>
     * The filepath is assumed to be inside /src/main/resources .
     */
    @Override
    public TransitModel readModel() throws IOException {

        // Read the json as a string from the file.
        String json = Files.readString(file.toPath());

        JSONObject model = new JSONObject(json);

        // Create the transit model
        TransitModel transitModel = new TransitModel();

        // Create a station factory for creating stations
        StationFactory factory = new StationFactory();

        // Get the array of stations
        JSONArray stations = model.getJSONArray("stations");
        for (int i = 0; i < stations.length(); i++) {

            // Reading stations is very simple, they only have a name
            // and coordinates
            JSONObject station = stations.getJSONObject(i);
            String name = station.getString("name");
            int x = station.getInt("x");
            int y = station.getInt("y");

            // Create the station
            Node node = transitModel.createNode(factory, name);
            node.setX(x);
            node.setY(y);
        }

        // Read the lines
        JSONArray lines = model.getJSONArray("lines");
        Set<Integer> linesMade = new HashSet<>();
        for (int i = 0; i < lines.length(); i++) {
            JSONObject line = lines.getJSONObject(i);

            // Get the line number
            int lineNum = line.getInt("number");

            // The line number must be unique so far
            Preconditions.checkArgument(!linesMade.contains(lineNum), "Line " + lineNum + " already exists");
            linesMade.add(lineNum);

            // Cyclic stations have their first and last stations connected
            boolean cyclic = line.optBoolean("cyclic", false);
            JSONArray stationsInLine = line.getJSONArray("stations");
            Preconditions.checkArgument(!stationsInLine.isEmpty(), "Line " + lineNum + " has no stations");

            Node firstNode = null;
            Node previousNode = null;

            for (int stationNum = 0; stationNum < stationsInLine.length(); stationNum++) {
                String nodeName = stationsInLine.getString(stationNum);
                Node node = transitModel.getNode(nodeName);
                Preconditions.checkArgument(node != null, "Node " + nodeName + " does not exist");

                if (node.getLineProfile(lineNum).isEmpty()) {
                    node.createLineProfile(lineNum);
                }

                if (firstNode == null) firstNode = node;

                if (previousNode != null) {
                    double distance = calculateDistance(node, previousNode);
                    createEdge(lineNum, previousNode, node, distance);
                }

                previousNode = node;

            }

            assert firstNode != null;

            if (cyclic) {
                double distance = calculateDistance(firstNode, previousNode);
                createEdge(lineNum, previousNode, firstNode, distance);
            } else {
                // Find the endpoints and link the forward and backward tracks of them

                NodeLineProfile firstProfile = firstNode.getLineProfile(lineNum).orElseThrow();
                firstProfile.getTrack(Direction.FORWARD).linkBackward(firstProfile.getTrack(Direction.BACKWARD));

                NodeLineProfile secondProfile = previousNode.getLineProfile(lineNum).orElseThrow();
                secondProfile.getTrack(Direction.FORWARD).linkForward(secondProfile.getTrack(Direction.BACKWARD));
            }
        }

        return transitModel;
    }

    /**
     * Calculates the distance between two nodes.
     *
     * @param node1 The first node
     * @param node2 The second node
     * @return The distance between the two nodes
     */
    private double calculateDistance(Node node1, Node node2) {
        int x = node1.getX();
        int y = node1.getY();

        int otherX = node2.getX();
        int otherY = node2.getY();

        return Math.sqrt(Math.pow(x - otherX, 2) + Math.pow(y - otherY, 2));
    }

    /**
     * Link two nodes by creating edge tracks between them in both directions.
     *
     * @param line   The line number
     * @param node1  The first node
     * @param node2  The second node
     * @param length The length of the edge tracks, usually the distance between the nodes
     */
    private void createEdge(int line, Node node1, Node node2, double length) {

        // Check preconditions
        Preconditions.checkArgument(node1.getTracker() == node2.getTracker(),
                "Nodes are not in the same model");

        Preconditions.checkArgument(node1.getLineProfile(line).isPresent(),
                "Node " + node1.getName() + " does not have line " + line);
        Preconditions.checkArgument(node2.getLineProfile(line).isPresent(),
                "Node " + node2.getName() + " does not have line " + line);

        // Get the node tracker
        NodeTracker model = node1.getTracker();

        // Create the intermediary tracks in both directions
        TrackSegment dir1 = new TrackSegment(model.getTrackRepo(),
                "Line-" + line + " " + node1.getName() + "-" + node2.getName(), length);
        model.getTrackRepo().addTrack(dir1);

        TrackSegment dir2 = new TrackSegment(model.getTrackRepo(),
                "Line-" + line + " " + node2.getName() + "-" + node1.getName(), length);
        model.getTrackRepo().addTrack(dir2);

        // Get the tracks for each direction of each node
        TrackSegment n1Dir1 = node1.getLineProfile(line)
                .get().getTrack(Direction.FORWARD);
        TrackSegment n1Dir2 = node1.getLineProfile(line)
                .get().getTrack(Direction.BACKWARD);

        TrackSegment n2Dir1 = node2.getLineProfile(line)
                .get().getTrack(Direction.FORWARD);

        TrackSegment n2Dir2 = node2.getLineProfile(line)
                .get().getTrack(Direction.BACKWARD);

        // Then link all the tracks together
        n1Dir1.linkForward(dir1);
        dir1.linkForward(n2Dir1);

        n1Dir2.linkBackward(dir2);
        dir2.linkBackward(n2Dir2);

    }
}
