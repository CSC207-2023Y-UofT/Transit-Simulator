package model.persistence;

import model.Direction;
import model.control.TransitModel;
import model.node.Node;
import model.node.NodeTracker;
import model.node.StationFactory;
import model.train.track.TrackSegment;
import org.json.JSONArray;
import org.json.JSONObject;
import util.Preconditions;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class JsonModelDataStore implements ModelDataStore {

    private final File file;

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
    public TransitModel readModel() {  // TODO HELP INFO IMPORTANT SUPER DUPER IMPORTANT TODO!!!!!!!

        JSONObject model = JSONRead

        TransitModel transitModel = new TransitModel();


        // Read the stations
        StationFactory factory = new StationFactory();

        JSONArray stations = model.getJSONArray("stations");
        for (int i = 0; i < stations.length(); i++) {
            JSONObject station = stations.getJSONObject(i);
            String name = station.getString("name");
            transitModel.createNode(factory, name);
        }

        // Read the lines
        JSONArray lines = model.getJSONArray("lines");
        Set<Integer> linesMade = new HashSet<>();
        for (int i = 0; i < lines.length(); i++) {
            JSONObject line = lines.getJSONObject(i);

            int lineNum = line.getInt("number");
            Preconditions.checkArgument(!linesMade.contains(lineNum), "Line " + lineNum + " already exists");
            linesMade.add(lineNum);

            boolean cyclic = line.optBoolean("cyclic", false);
            JSONArray stationsInLine = line.getJSONArray("stations");
            Preconditions.checkArgument(!stationsInLine.isEmpty(), "Line " + lineNum + " has no stations");

            Node firstNode = null;
            Node previousNode = null;

            for (int stationNum = 0; stationNum < stationsInLine.length(); stationNum++) {
                String nodeName = stationsInLine.getString(stationNum);
                Node node = transitModel.getNode(nodeName);
                Preconditions.checkArgument(node != null, "Node " + nodeName + " does not exist");

                if (firstNode == null) firstNode = node;

                if (previousNode != null) {
                    int x = node.getX();
                    int y = node.getY();

                    int otherX = previousNode.getX();
                    int otherY = previousNode.getY();

                    double distance = Math.sqrt(Math.pow(x - otherX, 2) + Math.pow(y - otherY, 2));

                    createEdge(lineNum, previousNode, node, distance);
                }

                previousNode = node;

            }

            assert firstNode != null;

            if (cyclic) {
                int x = firstNode.getX();
                int y = firstNode.getY();

                int otherX = previousNode.getX();
                int otherY = previousNode.getY();

                double distance = Math.sqrt(Math.pow(x - otherX, 2) + Math.pow(y - otherY, 2));

                createEdge(lineNum, previousNode, firstNode, distance);
            }
        }

        return transitModel;
    }

    private void createEdge(int line, Node node1, Node node2, double length) {
        Preconditions.checkArgument(node1.getTracker() == node2.getTracker(),
                "Nodes are not in the same model");

        Preconditions.checkArgument(node1.getLineProfile(line).isPresent(),
                "Node " + node1.getName() + " does not have line " + line);
        Preconditions.checkArgument(node2.getLineProfile(line).isPresent(),
                "Node " + node2.getName() + " does not have line " + line);

        NodeTracker model = node1.getTracker();

        TrackSegment dir1 = new TrackSegment(model.getTrackRepo(),
                node1.getName() + "-" + node2.getName(), length);
        model.getTrackRepo().addTrack(dir1);

        TrackSegment dir2 = new TrackSegment(model.getTrackRepo(),
                node2.getName() + "-" + node1.getName(), length);
        model.getTrackRepo().addTrack(dir2);

        TrackSegment n1Dir1 = node1.getLineProfile(line)
                .get().getTrack(Direction.FORWARD);
        TrackSegment n1Dir2 = node1.getLineProfile(line)
                .get().getTrack(Direction.BACKWARD);

        TrackSegment n2Dir1 = node2.getLineProfile(line)
                .get().getTrack(Direction.FORWARD);

        TrackSegment n2Dir2 = node2.getLineProfile(line)
                .get().getTrack(Direction.BACKWARD);

        n1Dir1.linkForward(dir1);
        dir1.linkForward(n2Dir1);

        n2Dir2.linkBackward(dir2);
        dir2.linkBackward(n1Dir2);

    }

    @Override
    public void writeModel(TransitModel model) {

    }
}
