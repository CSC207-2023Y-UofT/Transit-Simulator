package persistence.impl.file;

import entity.model.Direction;
import entity.model.control.TransitModel;
import entity.model.control.builder.TransitModelBuilder;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        TransitModelBuilder builder = new TransitModelBuilder();

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
            builder.station(name, x, y);
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

            List<String> stationNames = stationsInLine
                    .toList()
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());

            if (cyclic) {
                builder.cyclicLine(lineNum, stationNames);
            } else {
                builder.line(lineNum, stationNames);
            }
        }

        return builder.build();
    }

}
