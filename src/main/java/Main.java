import org.json.JSONObject;

import model.control.*;
import model.node.*;
import model.train.*;
import model.train.track.*;
import model.Direction;

import java.util.*;

/**
    The Main program that sets up all required classes and executes the program.

    What this should do: TODO

    To-do list:
    TODO the program
 */
public class Main {

    int TRAIN_INTERVAL = 300; // seconds, or distance if you will
    int DEFAULT_TRACK_LENGTH = 100;



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
    public static void readModel(String filename) {  // TODO HELP INFO IMPORTANT SUPER DUPER IMPORTANT TODO!!!!!!!
        // HELP the varaible names are very redundant for the sake of clarity, refactor to shorter names later
        NodeLineProfile anchorNodeLineProfile;                      // used as a start point for putting trains in the system
        Station anchorStation;                                      // used as a start point for putting trains in the system
        List<NodeLineProfile> lineProfiles = new ArrayList<>();     // for keeping track of created stations


        JSONObject model = new JSONObject(filename);  // read the JSON file

        transitTracker = new TransitTracker(); // create the controller

        // get the list of lines, one of the high-level entries in the JSON file
        List<Map> lines = model.get("lines");

        // for each line in the list of lines:
        for (Map line : lines) {  //TODO: maps from string to
            // get the line type (line or circle)
            String lineType = line.get("lineType");

            // get the line name
            String lineName = line.get("lineName");

            // get the line number
            int lineNumber = line.get("lineNumber");


            // get the list of stations for this line and create an iterator for it
            // TODO get list
            Iterator<Map> something = line.get("lineNodeList").iterator();

            // check if the iterator has a station
            if (!something.hasNext()) {
                // if not, then throw an exception
                throw new IllegalArgumentException("No stations found for line " + lineNumber + ". Check the JSON file.");
            }


            // get the first json station
            // TODO: how to say Object here is Union[String, int]?
            //       For example, Python has union of type parameters from the native Typing module, like
            //       def function(param: str) -> Union[str, int]:...
            Map<String, Object> currentJSONStation = something.next();

            String currentNodeName = currentJSONStation.get("nodeName");
            int currentNodeLength = currentJSONStation.get("nodeLength");  // TODO: decide whether to use this information
            String currentNodeType = currentJSONStation.get("nodeType");

            // check if the node type is a station
            if (!currentNodeType.equals("Station")) {
                throw new IllegalArgumentException("First node in line " + lineNumber + " is not a station. Check the JSON file.");
            }


            // create the station, its NodeLineProfile, record it as the anchor NLP, and add it to the list of line profiles
            anchorStation = new Station(transitTracker, currentNodeName);
            anchorNodeLineProfile = anchorStation.createLineProfile(lineNumber);  // Never instantiate it like "new NodeLineProfile(...)"
            lineProfiles.add(anchorNodeLineProfile);

            // create the TrackSegment.s between the previous station and the incoming one
            TrackSegment prevTrackSegmentForward = anchorNodeLineProfile.getTrack(Direction.FORWARD);
            TrackSegment prevTrackSegmentBackward = anchorNodeLineProfile.getTrack(Direction.BACKWARD);

            // Add the TrackSegment.s to the track repo
            transitTracker.getTrackRepo().addTrack(prevTrackSegmentForward);
            transitTracker.getTrackRepo().addTrack(prevTrackSegmentBackward);

            // keep track of the previous station's type and name and tracks since last station
            String prevNodeType = "Station";
            String prevStationName = currentNodeName;
            int tracksSinceLastStation = 0;


            // loop while the iterator has a next station (something.hasNext() )
            // Precondition: the first station has already been created
            while (something.hasNext()) {
                // read the next node from the iterator
                currentJSONStation = something.next();

                currentNodeName = currentJSONStation.get("nodeName");  // if the node type is a track, then this variable won't be used when connecting the node
                currentNodeLength = currentJSONStation.get("nodeLength");  // TODO: decide whether to use this information
                currentNodeType = currentJSONStation.get("nodeType");

                // calculate the station number for ease of use
                int stationNumber = lineProfiles.size();

                if (prevNodeType.equals("Track") && currentNodeType.equals("Track")) {  // because == isn't safe
                    // Create the track segments
                    TrackSegment currentTrackSegmentForward = new TrackSegment(transitTracker.getTrackRepo(), "line" + lineNumber + , currentNodeLength);

                } else if (prevNodeType.equals("Track") && currentNodeType.equals("Track")) {
//
                } else if (prevNodeType.equals("Station") && currentNodeType.equals("Track")) {

                } else if (prevNodeType.equals("Station") && currentNodeType.equals("Track")) {

                } else {

                }
//
//
//
//                    // create the station, its NodeLineProfile, and add it to the list of line profiles (or assign it to the variable prevNodeLineProfile, whatever we just need to keep a reference to it)
//                    Station currentStation = new Station(transitTracker, currentNodeName);
//                    NodeLineProfile currentNodeLineProfile= currentStation.createLineProfile(lineNumber);
//                    lineProfiles.add(currentNodeLineProfile);
//
//                    // connect the previous node to the new station
//                    if (prevNodeType.equals("Track")) {
//                        // get references to the current station's TrackSegments
//                        TrackSegment currentTrackSegmentForward = currentNodeLineProfile.getTrack(Direction.FORWARD);
//                        TrackSegment currentTrackSegmentBackward = currentNodeLineProfile.getTrack(Direction.BACKWARD);
//
//                        // TODO HELP add the station track segments to the track repo?
//
//                        // Link the previous tracks to the current station's tracks. Note that the linkage is
//                        // technically always in the forward direction; just that the track segments are pointing in
//                        // the reverse direction
//                        prevTrackSegmentForward.linkForward(currentTrackSegmentForward);
//                        currentTrackSegmentBackward.linkForward(prevTrackSegmentBackward);
//
//                        prevTrackSegmentForward = currentTrackSegmentForward;
//                        prevTrackSegmentBackward = currentTrackSegmentBackward;
//
//
//                    } else if (prevNodeType.equals("Station")) {
//                        // create a connecting TrackSegment
//                        TrackSegment
//
//                    } else {
//                        throw new IllegalArgumentException("Node type " + prevNodeType + " is not a valid node type. Check the JSON file.");
//                    }
//
//
//
//                    // important: if the line type is a circle, then the last station should be connected to the first station
//                    // lineType circle cornercase: add a final TrackSegment and connect the start and ends.
//
//
//                } else {
//                    throw new IllegalArgumentException("Node type " + currentNodeType + " is not a valid node type. Check the JSON file.");
//                }


            // the nodes and trackSegments are connected at this point

            // loop through the system from the anchor station and add trains to the system every TRAIN_INTERVAL seconds (or distance)

            // loop once if lineType = "Line" (and have the builder change direction), and twice if lineType = "Circle" (with second iteration in the backwards direction)
                // create a reference to the anchor station
                // add the train to the anchor station
                // record the direction the builder is going

                // record the distance away from the last train added to the start of this trackSegment

                // start loop
                    // if the distance away from the last train added to the start of this trackSegment is greater than TRAIN_INTERVAL
                        // add a train to the trackSegment at a distance whose length is the difference between the TRAIN_INTERVAL minus the recorded distance
                        // record the distance away from the last train added to the start of this trackSegment
                    // if not:
                        // move the reference to the next connected TrackSegment
                    // if the lineType is a line, then we have to handle the builder changing directions
                    // end condition: the reference is back at the anchor station
            }
    }



    public static void main(String[] args) {

        // TODO: Create a scenario (dates, times, fresh instances of Trains, etc) to complete the model
    }
}

