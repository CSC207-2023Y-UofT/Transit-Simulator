package entity.model.node;

import entity.model.Direction;
import entity.model.control.TransitModel;
import entity.model.node.line.NodeLineProfile;
import entity.model.node.station.StationFactory;
import entity.model.node.line.TrainArrival;
import entity.model.train.Train;
import entity.model.train.track.TrackSegment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;

import java.util.List;


public class NodeLineProfileTest {

    public static TransitModel transitModel;
    public static StationFactory stationFactory;
    public static Node station1;
    public static Node station2;
    public static Node station3;
    public static NodeLineProfile lineProfile1;
    public static NodeLineProfile lineProfile2;
    public static NodeLineProfile lineProfile3;
    public static Train train1;

    @DisplayName("NodeLineProfileTest Class Setup")
    @BeforeAll
    public static void setup() {
        transitModel = new TransitModel();
        stationFactory = new StationFactory();
        station1 = transitModel.createNode(stationFactory, "station1");
        station2 = transitModel.createNode(stationFactory, "station2");
        station3 = transitModel.createNode(stationFactory, "station3");

        lineProfile1 = station1.createLineProfile(1);
        lineProfile2 = station2.createLineProfile(1);
        lineProfile3 = station3.createLineProfile(1);

        TrackSegment t1f = new TrackSegment(transitModel.getTrackRepo(), "line1station1forward", 100);
        TrackSegment t1b = new TrackSegment(transitModel.getTrackRepo(), "line1station1backward", 100);
        TrackSegment t2f = new TrackSegment(transitModel.getTrackRepo(), "line1station2forward", 100);
        TrackSegment t2b = new TrackSegment(transitModel.getTrackRepo(), "line1station2backward", 100);

        transitModel.getTrackRepo().addTrack(t1f);
        transitModel.getTrackRepo().addTrack(t1b);
        transitModel.getTrackRepo().addTrack(t2f);
        transitModel.getTrackRepo().addTrack(t2b);


        TrackSegment s1f = lineProfile1.getTrack(Direction.FORWARD);
        TrackSegment s1b = lineProfile1.getTrack(Direction.BACKWARD);
        TrackSegment s2f = lineProfile2.getTrack(Direction.FORWARD);
        TrackSegment s2b = lineProfile2.getTrack(Direction.BACKWARD);
        TrackSegment s3f = lineProfile3.getTrack(Direction.FORWARD);
        TrackSegment s3b = lineProfile3.getTrack(Direction.BACKWARD);

        s1f.linkForward(t1f);
        t1f.linkForward(s2f);
        s2f.linkForward(t2f);
        t2f.linkForward(s3f);

        s3b.linkForward(t2b);
        t2b.linkForward(s2b);
        s2b.linkForward(t1b);
        t1b.linkForward(s1b);

        train1 = transitModel.createTrain(s1f, "t1", 120);
    }

    @Test
    public void testGetLineNumber() {
        Assertions.assertEquals(1, lineProfile1.getLineNumber());
    }

    @Test
    public void testGetNode() {
        Assertions.assertEquals(station1, lineProfile1.getNode());
    }

    @Test
    public void testGetTrack() {
        TrackSegment track = lineProfile1.getTrack(Direction.FORWARD);
        Assertions.assertEquals(track, transitModel.getTrackRepo().getTrack(track.getId()).orElse(null));
    }

    @Test
    public void testNextArrivals() {
        List<TrainArrival> arrivals = lineProfile2.nextArrivals(Direction.FORWARD, 2); // Find the next two trains
        Assertions.assertEquals(1, arrivals.size());
        Assertions.assertInstanceOf(TrainArrival.class, arrivals.get(0));
        Assertions.assertEquals(train1, arrivals.get(0).getTrain());
        Assertions.assertSame(station2, arrivals.get(0).getNode());
    }

    @DisplayName("NodeLineProfileTest Class Teardown")
    @AfterAll
    public static void teardown() {
        transitModel = null;
        stationFactory = null;
        station1 = null;
        station2 = null;
        station3 = null;
        lineProfile1 = null;
        lineProfile2 = null;
        lineProfile3 = null;
        train1 = null;
    }
}
