
import org.json.JSONObject;

import model.control.*;
import model.node.*;
import model.train.*;
import model.train.track.*;
import model.Direction;

import java.util.List;
import java.util.ArrayList;

/**
    The Main program that sets up all required classes and executes the program.

    What this should do: TODO

    To-do list:
    TODO the program
 */
public class Main {

    int TRAIN_INTERVAL = 300; // seconds, or distance if you will



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
        NodeLineProfile anchorNodeLineProfile;                      // used as a start point for putting trains in the system
        Station anchorStation;                                      // used as a start point for putting trains in the system
        NodeLineProfile currentNodeLineProfile;                     // used for keeping track of the current station
        Station currentStation;                                     // used for keeping track of the current station
        TrackSegment prevStationTrackForward;                       // used for connecting the previous station to the incoming one
        TrackSegment prevStationTrackBackward;                      // used for connecting the previous station to the incoming one
        List<NodeLineProfile> lineProfiles = new ArrayList<>();     // for keeping track of created stations
        int lineNumber;                                             // for keeping track of the line number
        String lineName;                                            // for keeping track of the line name
        String lineType;                                            // for keeping track of the line type
        String stationName;                                         // for keeping track of the station name


        JSONObject model = new JSONObject(filename);

        // Create the controller
        transitTracker = new TransitTracker();

        // get the list of lines, which is the only high-level entry in the JSON file

        // for each line in the list of lines:
            // get the line type (line or circle)
            // get the line number
            // get the line name



            // Create an iterator that reads the list of station names from the json file

            // Check if the iterator has any stations (something.hasNext() )
            // if not, then throw an exception
            throw new IllegalArgumentException("No stations found for line " + lineNumber + ". Check the JSON file.");

            // get the first station name
            stationName = something.next();

            // create the station
            anchorStation = new Station(transitTracker, stationName);
            currentStation = anchorStation;

            // create its NodeLineProfile, and record it as the anchorNodeLineProfile
            anchorNodeLineProfile = new NodeLineProfile(anchorStation, lineNumber);
            currentNodeLineProfile = anchorNodeLineProfile;

            // add it to the list of line profiles
            lineProfiles.add(anchorNodeLineProfile);
            prevStationTrackForward = anchorStation.getTrack(Direction.FORWARD);
            prevStationTrackBackward = anchorStation.getTrack(Direction.BACKWARD);


            // loop while the iterator has a next station (something.hasNext() )
            while (something.hasNext()) {
                // get the next station name
                stationName = something.next();

                // calculate the station number for ease of use
                int stationNumber = lineProfiles.size();

                // Create the TrackSegment.s between the previous station and the incoming one
                prevStationTrackForward = new TrackSegment(transitTracker.getTrackRepo(), "line" + lineNumber + "station" + stationName + "forwards", 100);
                prevStationTrackBackward = new TrackSegment(transitTracker.getTrackRepo(), "line" + lineNumber + "station" + stationName + "backwards", 100);

                // Add the TrackSegment.s to the track repo
                transitTracker.getTrackRepo().addTrack(prevStationTrackForward);
                transitTracker.getTrackRepo().addTrack(prevStationTrackBackward);

                // create the new station



            // important: if the line type is a circle, then the last station should be connected to the first station
            // lineType circle cornercase: add a final TrackSegment and connect the start and ends.




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

