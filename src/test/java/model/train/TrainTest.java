package model.train;

import model.Direction;
import model.control.TransitTracker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

public class TrainTest {
    @DisplayName("Creating a new train")  // TODO: coding in progress
    @BeforeAll
    public static void setup() {
        TransitTracker transitTracker = new TransitTracker();
        Train train = transitTracker.createTrain(TrackSegment trackSegment, Direction.FORWARD, 120);
    }

    @Test
    public void testGetStatus() {
        Assertions.assertEquals(Train.Status.IN_SERVICE, train.getStatus());
    }

    @Test
    public void testSetStatus() {
        train.setStatus(Train.Status.OUT_OF_SERVICE);
        Assertions.assertEquals(Train.Status.OUT_OF_SERVICE, train.getStatus());
    }

    @Test
    public void testGetPassengerList() {
        Assertions.assertEquals(0, train.getPassengerList().size());
    }

    @Test
    public void testAddPassenger() {
        Passenger passenger = new Passenger(1, 1, 1, 1, 1);
        train.addPassenger(passenger);
        Assertions.assertEquals(1, train.getPassengerList().size());
    }

    @Test
    public void testGetCapacity() {
        Assertions.assertEquals(1, train.getCapacity());
    }

    @Test
    public void testGetPosition() {
        Train train = new Train(1, 1, 1, 1, 1, 1);
        Assertions.assertEquals(null, train.getPosition());
    }
}
