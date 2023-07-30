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

import java.util.List;

public class TrainTest {
    private static Train trainForwards;
    private static Train trainBackwards;

    @DisplayName("TrainTest Class Setup")
    @BeforeAll
    public static void setup() {
        // Create the controller
        TransitTracker transitTracker = new TransitTracker();

        // Create the stations
        Station station1 = new Station(transitTracker, "station1");
        Station station2 = new Station(transitTracker, "station2");
        Station station3 = new Station(transitTracker, "station3");

        // Create the line profiles: l1: Line 1, s1: Station 1
        NodeLineProfile l1s1 = station1.createLineProfile(1);
        NodeLineProfile l1s2 = station2.createLineProfile(1);
        NodeLineProfile l1s3 = station3.createLineProfile(1);

        // Create the tracks
        TrackSegment t1f = new TrackSegment(transitTracker.getTrackRepo(), "l1-s1-for", 100);
        TrackSegment t2f = new TrackSegment(transitTracker.getTrackRepo(), "l1-s2-for", 100);
        TrackSegment t3f = new TrackSegment(transitTracker.getTrackRepo(), "l1-s3-for", 100);
        TrackSegment t1b = new TrackSegment(transitTracker.getTrackRepo(), "l1-s1-back", 100);
        TrackSegment t2b = new TrackSegment(transitTracker.getTrackRepo(), "l1-s2-back", 100);
        TrackSegment t3b = new TrackSegment(transitTracker.getTrackRepo(), "l1-s3-back", 100);

        // Add the tracks to the repo
        transitTracker.getTrackRepo().addTrack(t1f);
        transitTracker.getTrackRepo().addTrack(t2f);
        transitTracker.getTrackRepo().addTrack(t3f);
        transitTracker.getTrackRepo().addTrack(t1b);
        transitTracker.getTrackRepo().addTrack(t2b);
        transitTracker.getTrackRepo().addTrack(t3b);

        // Get references to the track segments of each station
        TrackSegment s1f = l1s1.getTrack(Direction.FORWARD);
        TrackSegment s2f = l1s2.getTrack(Direction.FORWARD);
        TrackSegment s3f = l1s3.getTrack(Direction.FORWARD);
        TrackSegment s1b = l1s1.getTrack(Direction.BACKWARD);
        TrackSegment s2b = l1s2.getTrack(Direction.BACKWARD);
        TrackSegment s3b = l1s3.getTrack(Direction.BACKWARD);

        // Link Forwards
        s1f.linkForward(t1f);
        t1f.linkForward(s2f);
        s2f.linkForward(t2f);
        t2f.linkForward(s3f);
        s3f.linkForward(t3f);
        t3f.linkForward(s1f);

        // Link Backwards: Note that the linkage is technically always in the forward direction; just that the track
        //                 segments are pointing in the reverse direction
        s1b.linkForward(t3b);
        t3b.linkForward(s3b);
        s3b.linkForward(t2b);
        t2b.linkForward(s2b);
        s2b.linkForward(t1b);
        t1b.linkForward(s1b);

        // Create the trains
        trainForwards = transitTracker.createTrain(s1f, 120);
        trainBackwards = transitTracker.createTrain(s1b, 120);
    }

    @Test
    public void testGetStatus() {
        Assertions.assertEquals(Train.Status.OUT_OF_SERVICE, trainForwards.getStatus());
        Assertions.assertEquals(Train.Status.OUT_OF_SERVICE, trainBackwards.getStatus());
    }

    @Test
    public void testSetStatus() {
        trainForwards.setStatus(Train.Status.SCHEDULED_MAINTENANCE);  // We were so close to greatness... :( just one space too right
        Assertions.assertEquals(Train.Status.SCHEDULED_MAINTENANCE, trainForwards.getStatus());
        trainForwards.setStatus(Train.Status.UNDER_MAINTENANCE);
        Assertions.assertEquals(Train.Status.UNDER_MAINTENANCE, trainForwards.getStatus());
        trainForwards.setStatus(Train.Status.OUT_OF_SERVICE);
        Assertions.assertEquals(Train.Status.OUT_OF_SERVICE, trainForwards.getStatus());
        trainForwards.setStatus(Train.Status.IN_SERVICE);
        Assertions.assertEquals(Train.Status.IN_SERVICE, trainForwards.getStatus());
    }

    @Test
    public void testGetPassengerList() {
        List<Passenger> passengerList = trainForwards.getPassengerList();
        Assertions.assertEquals(0, passengerList.size());
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
