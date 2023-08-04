import model.Direction;
import model.node.Node;
import model.node.NodeTracker;
import model.node.Station;
import model.node.StationFactory;
import model.persistence.JsonModelDataStore;
import model.train.track.TrackSegment;
import org.json.JSONArray;
import org.json.JSONObject;

import model.control.*;
import util.Preconditions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 * The Main program that sets up all required classes and executes the program.
 * <p>
 * What this should do: TODO
 * <p>
 * To-do list:
 * TODO the program
 */
public class Main {


    public static void main(String[] args) {

        InputStream str = Main.class.getResourceAsStream("Model 1.json");
        File file = new File("model-1.json");
        try {
            assert str != null;
            byte[] bytes = str.readAllBytes();
            Files.write(file.toPath(), bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

