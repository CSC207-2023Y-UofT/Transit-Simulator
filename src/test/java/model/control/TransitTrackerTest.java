package model.control;

import model.Direction;
import model.control.TransitTracker;
import model.train.Train;
import model.node.Station;
import model.node.NodeLineProfile;
import model.train.track.TrackSegment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;


public class TransitTrackerTest {

    private static TransitTracker transitTracker;
    private static Train trainForwards;

    @DisplayName("TransitTrackerTest Class Setup")
    @BeforeAll
    public static void setup() {
        // Create the controller
        transitTracker = new TransitTracker();

        // Refer to //TODO image here for visualization.

        // Create the stations
        Station station1 = new Station(transitTracker, "station1");
        Station station2 = new Station(transitTracker, "station2");
        Station station3 = new Station(transitTracker, "station3");

        // Create the line profiles: l1: Line 1, s1: Station 1
        NodeLineProfile l1s1 = station1.createLineProfile(1);
        NodeLineProfile l1s2 = station2.createLineProfile(1);
        NodeLineProfile l1s3 = station3.createLineProfile(1);

        // Create the tracks
        TrackSegment t1f = new TrackSegment(transitTracker.getTrackRepo(), "l1-s1-t1-for", 100);
        TrackSegment t2f = new TrackSegment(transitTracker.getTrackRepo(), "l1-s2-t2-for", 100);

        // Add the tracks to the repo
        transitTracker.getTrackRepo().addTrack(t1f);
        transitTracker.getTrackRepo().addTrack(t2f);

        // Get references to the track segments of each station
        TrackSegment s1f = l1s1.getTrack(Direction.FORWARD);
        TrackSegment s2f = l1s2.getTrack(Direction.FORWARD);
        TrackSegment s3f = l1s3.getTrack(Direction.FORWARD);  // TODO add the tracks of stations to the repo? not clear
                                                              //      or possible add them to the nodeMap of transitTracker

        // s: station, t: track, f: forward, b: backward

        // Link Forwards
        s1f.linkForward(t1f);
        t1f.linkForward(s2f);
        s2f.linkForward(t2f);
        t2f.linkForward(s3f);

        // Create the trains
        trainForwards = transitTracker.createTrain(s1f, 120);
    }

    @Test
    public void test() {
        // TODO: Write tests
    }


    @DisplayName("TransitTrackerTest Class Teardown")
    @AfterAll
    public static void teardown() {
        transitTracker = null;
    }

}
