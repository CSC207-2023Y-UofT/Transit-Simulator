package model.train;

import entity.model.Direction;
import entity.model.control.TransitModel;
import entity.model.node.Node;
import entity.model.node.line.NodeLineProfile;
import entity.model.node.station.StationFactory;
import entity.model.train.Passenger;
import entity.model.train.TrainStatus;
import entity.model.train.Train;
import entity.model.train.TrainPosition;
import entity.model.train.track.TrackSegment;
import entity.employee.TrainOperator;
import entity.employee.TrainEngineer;

import entity.ticket.Ticket;
import entity.ticket.TicketType;
import org.junit.jupiter.api.*;

import java.util.HashSet;


public class TrainTest {
    private static TransitModel transitModel;
    private static Train trainForwards;
    private static Train trainBackwards;
    private static TrainOperator operator;
    private static TrainEngineer engineer;

    @DisplayName("TrainTest Class Setup")
    @BeforeAll
    public static void setup() {
        // Create the controller
        transitModel = new TransitModel();

        // Refer to ![](images/TrainTest Setup Diagram.png) for visualization.

        // Create the station factory
        StationFactory factory = new StationFactory();

        // Create the stations
        Node station1 = transitModel.createNode(factory, "station1");
        Node station2 = transitModel.createNode(factory, "station2");
        Node station3 = transitModel.createNode(factory, "station3");


        // Create the line profiles: l1: Line 1, s1: Station 1
        NodeLineProfile l1s1 = station1.createLineProfile(1);
        NodeLineProfile l1s2 = station2.createLineProfile(1);
        NodeLineProfile l1s3 = station3.createLineProfile(1);

        // Create the tracks
        TrackSegment t1f = new TrackSegment(transitModel.getTrackRepo(), "l1-s1-for", 100);
        TrackSegment t2f = new TrackSegment(transitModel.getTrackRepo(), "l1-s2-for", 100);
        TrackSegment t3f = new TrackSegment(transitModel.getTrackRepo(), "l1-s3-for", 100);
        TrackSegment t1b = new TrackSegment(transitModel.getTrackRepo(), "l1-s1-back", 100);
        TrackSegment t2b = new TrackSegment(transitModel.getTrackRepo(), "l1-s2-back", 100);
        TrackSegment t3b = new TrackSegment(transitModel.getTrackRepo(), "l1-s3-back", 100);

        // Add the tracks to the repo
        transitModel.getTrackRepo().addTrack(t1f);
        transitModel.getTrackRepo().addTrack(t2f);
        transitModel.getTrackRepo().addTrack(t3f);
        transitModel.getTrackRepo().addTrack(t1b);
        transitModel.getTrackRepo().addTrack(t2b);
        transitModel.getTrackRepo().addTrack(t3b);

        // Station tracks are already added to the repo in the NodeLineProfile constructor

        // Get references to the track segments that belong to each station
        TrackSegment s1f = l1s1.getTrack(Direction.FORWARD);
        TrackSegment s2f = l1s2.getTrack(Direction.FORWARD);
        TrackSegment s3f = l1s3.getTrack(Direction.FORWARD);
        TrackSegment s1b = l1s1.getTrack(Direction.BACKWARD);
        TrackSegment s2b = l1s2.getTrack(Direction.BACKWARD);
        TrackSegment s3b = l1s3.getTrack(Direction.BACKWARD);

        // s: station, t: track, f: forward, b: backward

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
        trainForwards = transitModel.createTrain(s1f, "tf", 120);
        trainBackwards = transitModel.createTrain(s1b, "tb", 120);

        // Create the employees
        operator = new TrainOperator(0, "");
        engineer = new TrainEngineer(1, "");
    }

    // Begin testing

    @Test
    public void testGetTransitModel() {
        Assertions.assertSame(transitModel, trainForwards.getTransitTracker());
        Assertions.assertSame(transitModel, trainBackwards.getTransitTracker());
    }

    @Test
    public void testGetCapacity() {
        Assertions.assertEquals(120, trainForwards.getCapacity());
        Assertions.assertEquals(120, trainBackwards.getCapacity());
    }

    @Test
    public void testGetPosition() {
        Assertions.assertInstanceOf(TrainPosition.class, trainForwards.getPosition());
        Assertions.assertInstanceOf(TrainPosition.class, trainBackwards.getPosition());
    }

    @Test
    public void testGetStatus() {
        Assertions.assertEquals(TrainStatus.OUT_OF_SERVICE, trainForwards.getStatus());
        Assertions.assertEquals(TrainStatus.OUT_OF_SERVICE, trainBackwards.getStatus());
    }

    @Test
    public void testSetStatus() {
        trainForwards.setStatus(TrainStatus.SCHEDULED_MAINTENANCE);
        Assertions.assertEquals(TrainStatus.SCHEDULED_MAINTENANCE, trainForwards.getStatus());
        trainForwards.setStatus(TrainStatus.UNDER_MAINTENANCE);
        Assertions.assertEquals(TrainStatus.UNDER_MAINTENANCE, trainForwards.getStatus());
        trainForwards.setStatus(TrainStatus.IN_SERVICE);
        Assertions.assertEquals(TrainStatus.IN_SERVICE, trainForwards.getStatus());
        trainForwards.setStatus(TrainStatus.OUT_OF_SERVICE);
        Assertions.assertEquals(TrainStatus.OUT_OF_SERVICE, trainForwards.getStatus());
    }

    @Test
    public void testGetPassengerListZero() {
        Assertions.assertEquals(new HashSet<>(), trainForwards.getPassengerList());
        Assertions.assertEquals(0, trainForwards.getPassengerList().size());
    }

    @Test
    public void testAddAndRemovePassenger() {
        // setup
        Passenger passengerAdult = new Passenger(new Ticket(TicketType.ADULT), 0);
        Passenger passengerChild = new Passenger(new Ticket(TicketType.CHILD), 1);
        Passenger passengerStudent = new Passenger(new Ticket(TicketType.STUDENT), 2);
        Passenger passengerSenior = new Passenger(new Ticket(TicketType.STUDENT), 3);

        trainForwards.addPassenger(passengerAdult);
        trainForwards.addPassenger(passengerChild);
        trainForwards.addPassenger(passengerStudent);
        trainForwards.addPassenger(passengerSenior);

        // test
        Assertions.assertEquals(4, trainForwards.getPassengerList().size());

        Assertions.assertTrue(trainForwards.getPassengerList().contains(passengerAdult));
        Assertions.assertTrue(trainForwards.getPassengerList().contains(passengerChild));
        Assertions.assertTrue(trainForwards.getPassengerList().contains(passengerStudent));
        Assertions.assertTrue(trainForwards.getPassengerList().contains(passengerSenior));

        // convenient teardown
        trainForwards.removePassenger(passengerAdult);
        trainForwards.removePassenger(passengerChild);
        trainForwards.removePassenger(passengerStudent);
        trainForwards.removePassenger(passengerSenior);

        Assertions.assertTrue(trainForwards.getPassengerList().isEmpty());
    }



    @DisplayName("TrainTest Class Teardown")
    @AfterAll
    public static void teardown() {
        // Tearing down the class since we have static variables
        transitModel = null;
        trainForwards = null;
        trainBackwards = null;
        operator = null;
        engineer = null;
    }
  }
