package model.train;

import model.Direction;
import model.control.TransitTracker;
import model.node.NodeLineProfile;
import model.node.Station;
import model.train.track.TrackSegment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

public class TrainTest {
    private static Train trainForwards;
    private static Train trainBackwards;

    @DisplayName("TrainTest Class Setup")
    @BeforeAll
    public static void setup() {
        TransitTracker transitTracker = new TransitTracker();
        TrackSegment t1f = new TrackSegment(transitTracker.getTrackRepo(), "1-1-for", 100);
        TrackSegment t2f = new TrackSegment(transitTracker.getTrackRepo(), "1-2-for", 100);
        TrackSegment t3f = new TrackSegment(transitTracker.getTrackRepo(), "1-3-for", 100);
        TrackSegment t1b = new TrackSegment(transitTracker.getTrackRepo(), "1-1-back", 100);
        TrackSegment t2b = new TrackSegment(transitTracker.getTrackRepo(), "1-2-back", 100);
        TrackSegment t3b = new TrackSegment(transitTracker.getTrackRepo(), "1-3-back", 100);


        transitTracker.getTrackRepo().addTrack(t1f);
        transitTracker.getTrackRepo().addTrack(t2f);
        transitTracker.getTrackRepo().addTrack(t3f);
        transitTracker.getTrackRepo().addTrack(t1b);
        transitTracker.getTrackRepo().addTrack(t2b);
        transitTracker.getTrackRepo().addTrack(t3b);

        Station station1 = new Station(transitTracker, "station1");
        Station station2 = new Station(transitTracker, "station2");
        Station station3 = new Station(transitTracker, "station3");

        NodeLineProfile sl1 = station1.createLineProfile(1);
        NodeLineProfile sl2 = station2.createLineProfile(1);
        NodeLineProfile sl3 = station3.createLineProfile(1);

        TrackSegment s1f = sl1.getTrack(Direction.FORWARD);
        TrackSegment s2f = sl2.getTrack(Direction.FORWARD);
        TrackSegment s3f = sl3.getTrack(Direction.FORWARD);
        TrackSegment s1b = sl1.getTrack(Direction.BACKWARD);
        TrackSegment s2b = sl2.getTrack(Direction.BACKWARD);
        TrackSegment s3b = sl3.getTrack(Direction.BACKWARD);

        // Forwards
        s1f.linkForward(t1f);
        t1f.linkForward(s2f);
        s2f.linkForward(t2f);
        t2f.linkForward(s3f);
        s3f.linkForward(t3f);
        t3f.linkForward(s1f);

        // Backwards
        s1b.linkForward(t3b);
        t3b.linkForward(s3b);
        s3b.linkForward(t2b);
        t2b.linkForward(s2b);
        s2b.linkForward(t1b);
        t1b.linkForward(s1b);

        trainForwards = transitTracker.createTrain(s1f, 120);
        trainBackwards = transitTracker.createTrain(s1b, 120);
    }

    @Test
    public void testGetStatus() {
        Assertions.assertEquals(Train.Status.IN_SERVICE, train.getStatus());
    }

    @Test
    public void testSetStatus() {
        train.setStatus(Train.Status.OUT_OF_SERVICE);
        Assertions.assertEquals(Train.Status.OUT_OF_SERVICE, train.getStatus());
        train.setStatus(Train.Status.SCHEDULED_MAINTENANCE);
        Assertions.assertEquals(Train.Status.SCHEDULED_MAINTENANCE, train.getStatus());
        train.setStatus(Train.Status.UNDER_MAINTENANCE);
        Assertions.assertEquals(Train.Status.UNDER_MAINTENANCE, train.getStatus());
        train.setStatus(Train.Status.IN_SERVICE);
        Assertions.assertEquals(Train.Status.IN_SERVICE, train.getStatus());
    }

    @Test
    public void testGetPassengerList() {
        Assertions.assertEquals(0, train.getPassengerList().size());
    }

//    @Test
//    public void testAddPassenger() {
//        Passenger passenger = new Passenger(1, 1, 1, 1, 1);
//        train.addPassenger(passenger);
//        Assertions.assertEquals(1, train.getPassengerList().size());
//    }
//
//    @Test
//    public void testGetCapacity() {
//        Assertions.assertEquals(1, train.getCapacity());
//    }
//
//    @Test
//    public void testGetPosition() {
//        Train train = new Train(1, 1, 1, 1, 1, 1);
//        Assertions.assertEquals(null, train.getPosition());
//    }
  }
