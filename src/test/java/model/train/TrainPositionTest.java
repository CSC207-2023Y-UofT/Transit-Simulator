package model.train;

import model.Direction;
import model.control.TransitModel;
import model.node.Node;
import model.node.NodeLineProfile;
import model.node.StationFactory;
import model.train.track.TrackSegment;

import org.junit.jupiter.api.*;

public class TrainPositionTest {
    public static TrackSegment track1;
    public static TrainPosition trainPosition1;

    @BeforeEach
    public void setup() {
        track1 = new TrackSegment(null, "track1", 100);
        trainPosition1 = new TrainPosition(track1, 50);
    }

    @Test
    public void testGetTrack() {
        Assertions.assertSame(track1, trainPosition1.getTrack());
    }

    @Test
    public void testGetPositionOnTrack() {
        Assertions.assertEquals(50, trainPosition1.getPositionOnTrack());
    }

    @Test
    public void testWithOffsetFirstPrecondition() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            trainPosition1.withOffset(-1);
        });
    }

    @Test
    public void testWithOffset() {
        TrainPosition trainPosition2 = trainPosition1.withOffset(57);
        Assertions.assertEquals(57, trainPosition2.getPositionOnTrack());
    }

    @Test
    public void testConstructorFirstPrecondition() {
        TrackSegment track = new TrackSegment(null, "track", 100);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new TrainPosition(track, -1);
        });
    }

    @Test
    public void testTrackEndOffset() {
        TrainPosition trainPosition2 = new TrainPosition(track1, 50);
        Assertions.assertEquals(50, trainPosition2.trackEndOffset(Direction.FORWARD));
        Assertions.assertEquals(-50, trainPosition2.trackEndOffset(Direction.BACKWARD));
        trainPosition2 = trainPosition2.move(1).get();
        Assertions.assertEquals(49, trainPosition2.trackEndOffset(Direction.FORWARD));
        Assertions.assertEquals(-51, trainPosition2.trackEndOffset(Direction.BACKWARD));
    }

    @Test
    public void testDistanceToEndOfTrack() {
        TrainPosition trainPosition2 = new TrainPosition(track1, 51);
        Assertions.assertEquals(49, trainPosition2.distanceToEndOfTrack(Direction.FORWARD));
        Assertions.assertEquals(51, trainPosition2.distanceToEndOfTrack(Direction.BACKWARD));
    }

    @Test
    public void testMoveZero() {
        TrainPosition trainPosition2 = trainPosition1.move(0).get();
        Assertions.assertEquals(50, trainPosition2.getPositionOnTrack());
    }

    @Test
    public void testMoveZeroDifferentObjects() {
        TrainPosition trainPosition2 = trainPosition1.move(0).get();
        Assertions.assertNotSame(trainPosition1, trainPosition2);
    }

    @Test
    public void testMove_ToEndpoint() {
        TransitModel transitModel = new TransitModel();
        StationFactory stationFactory = new StationFactory();
        Node station1 = transitModel.createNode(stationFactory, "station1");  // Station length is equal to the static Train.LENGTH == 100
        Node station2 = transitModel.createNode(stationFactory, "station2");
        NodeLineProfile lineProfile1 = station1.createLineProfile(1);
        NodeLineProfile lineProfile2 = station2.createLineProfile(1);
        TrackSegment t1f = new TrackSegment(transitModel.getTrackRepo(), "trackSegment1", 100);
        transitModel.getTrackRepo().addTrack(t1f);
        TrackSegment s1f = lineProfile1.getTrack(Direction.FORWARD);
        TrackSegment s2f = lineProfile2.getTrack(Direction.FORWARD);
        s1f.linkForward(t1f);
        t1f.linkForward(s2f);
        TrainPosition trainPosition2 = new TrainPosition(s1f, 0);
        Assertions.assertEquals(0, trainPosition2.getPositionOnTrack());
        trainPosition2 = trainPosition2.move(10, false).get();
        Assertions.assertEquals(10, trainPosition2.getPositionOnTrack());
        Assertions.assertSame(s1f, trainPosition2.getTrack());
        trainPosition2 = trainPosition2.move(100, false).get();
        Assertions.assertEquals(10, trainPosition2.getPositionOnTrack());
        Assertions.assertSame(t1f, trainPosition2.getTrack());
        trainPosition2 = trainPosition2.move(100, false).get();
        Assertions.assertEquals(10, trainPosition2.getPositionOnTrack());
        Assertions.assertSame(s2f, trainPosition2.getTrack());
        Assertions.assertFalse(trainPosition2.move(100, false).isPresent());
    }

    @Test
    public void testMove_TrackOccupied() {
        TransitModel transitModel = new TransitModel();
        StationFactory stationFactory = new StationFactory();
        Node station1 = transitModel.createNode(stationFactory, "station1");  // Station length is equal to the static Train.LENGTH == 100
        Node station2 = transitModel.createNode(stationFactory, "station2");
        NodeLineProfile lineProfile1 = station1.createLineProfile(1);
        NodeLineProfile lineProfile2 = station2.createLineProfile(1);
        TrackSegment t1f = new TrackSegment(transitModel.getTrackRepo(), "trackSegment1", 100);
        transitModel.getTrackRepo().addTrack(t1f);
        TrackSegment s1f = lineProfile1.getTrack(Direction.FORWARD);
        TrackSegment s2f = lineProfile2.getTrack(Direction.FORWARD);
        s1f.linkForward(t1f);
        t1f.linkForward(s2f);
        Train trainForward = transitModel.createTrain(s2f, "trainForward", 120);
        TrainPosition trainPosition2 = new TrainPosition(s1f, 0);
        Assertions.assertEquals(0, trainPosition2.getPositionOnTrack());
        trainPosition2 = trainPosition2.move(150, false).get();
        Assertions.assertEquals(50, trainPosition2.getPositionOnTrack());
        Assertions.assertSame(t1f, trainPosition2.getTrack());
        Assertions.assertFalse(trainPosition2.move(100, false).isPresent());
    }

    @Test
    public void testEquals() {
        TrainPosition trainPosition2 = new TrainPosition(track1, 50);
        Assertions.assertEquals(trainPosition1, trainPosition2);
        TrainPosition trainPosition3 = new TrainPosition(track1, 51);
        Assertions.assertNotEquals(trainPosition1, trainPosition3);
        TrackSegment track2 = new TrackSegment(null, "track2", 100);
        TrainPosition trainPosition4 = new TrainPosition(track2, 50);
        Assertions.assertNotEquals(trainPosition1, trainPosition4);
    }

    @Test
    public void testHashCode() {
        TrainPosition trainPosition2 = new TrainPosition(track1, 50);
        Assertions.assertEquals(trainPosition1, trainPosition2);
        int hashCode1 = trainPosition1.hashCode();
        int hashCode2 = trainPosition2.hashCode();
        Assertions.assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testEntryPoint() {
        TrainPosition trainPosition2 = TrainPosition.entryPoint(track1, Direction.FORWARD);
        Assertions.assertEquals(0, trainPosition2.getPositionOnTrack());
        Assertions.assertSame(track1, trainPosition2.getTrack());
    }

    @Test
    public void testConstructor() {

    }
}
