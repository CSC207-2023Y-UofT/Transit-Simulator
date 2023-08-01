import org.json.JSONObject;

import model.control.*;
import model.node.*;
import model.train.*;

/**
    The Main program that sets up all required classes and executes the program.

    What this should do:

    To-do list:
    the program


 */
public class Main {

    /** The TransitTracker (program controller) that holds all the nodes and trains. */
    public static TransitTracker transitTracker;

    /**
     * Reads the model from a JSON file and creates the model.
     * Sets the static variables to hold a specific model.
     * <br>
     * The filepath is assumed to be inside /src/main/resources .
     *
     * @param filename The name of the JSON file to read from.
     */
    public static void readModel(String filename) {
        JSONObject model = new JSONObject(filename);


    }



    public static void main(String[] args) {

        // TODO: Create a scenario (dates, times, fresh instances of Trains, etc) to complete the model
    }
}

