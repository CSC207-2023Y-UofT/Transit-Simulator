package model.control;

import model.Direction;
import model.train.TrackRepo;
import model.train.Train;
import model.node.Station;
import model.node.NodeLineProfile;
import model.train.track.TrackSegment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;


public class TransitModelTest {

    private static TransitModel transitModel;
    private static Train trainForwards;

    @DisplayName("TransitModelTest Class Setup")
    @BeforeAll
    public static void setup() {
        // Create the controller
        transitModel = new TransitModel();

        // Refer to ![](images/TrainModelTest Setup Diagram.png) for visualization.

        // Create the stations
        Station station1 = new Station(transitModel, "station1");
        Station station2 = new Station(transitModel, "station2");
        Station station3 = new Station(transitModel, "station3");

        // Create the line profiles: l1: Line 1, s1: Station 1
        NodeLineProfile l1s1 = station1.createLineProfile(1);
        NodeLineProfile l1s2 = station2.createLineProfile(1);
        NodeLineProfile l1s3 = station3.createLineProfile(1);

        // Create the tracks
        TrackSegment t1f = new TrackSegment(transitModel.getTrackRepo(), "l1-s1-t1-for", 100);
        TrackSegment t2f = new TrackSegment(transitModel.getTrackRepo(), "l1-s2-t2-for", 100);

        // Add the tracks to the repo
        transitModel.getTrackRepo().addTrack(t1f);
        transitModel.getTrackRepo().addTrack(t2f);

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
        trainForwards = transitModel.createTrain(s1f, 120);
    }


    @Test
    public void testGetTrackRepo() {
        Assertions.assertInstanceOf(TrackRepo.class, transitModel.getTrackRepo());
    }

    @Test
    public void testTrackRepoSize() {
        Assertions.assertEquals(transitModel.getTrackRepo().getTracks().size(), 5);  // 3 for stations, 2 for tracks
    }


    @DisplayName("TransitModelTest Class Teardown")
    @AfterAll
    public static void teardown() {
        transitModel = null;
    }

}
