package model.train.track;
import model.train.Passenger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import ticket.AdultTicket;

public class PassengerTest {
    public static Passenger passenger;

    @DisplayName("PassengerTest Class Setup")
    @BeforeEach
    public void setup() {
        passenger = new Passenger(null, 3);
    }

    @Test
    public void testGetStationsToTravel() {
        Assertions.assertEquals(3, passenger.getStationsToTravel());
    }

    @Test
    public void testSetStationsToTravel() {
        passenger.setStationsToTravel(2);
        Assertions.assertEquals(2, passenger.getStationsToTravel());
    }

    @Test
    public void testDecrementStationsToTravel() {
        passenger.decrementStationsToTravel();
        Assertions.assertEquals(2, passenger.getStationsToTravel());
    }

    @Test
    public void testWillAlight() {
        Assertions.assertFalse(passenger.willAlight());
        passenger.setStationsToTravel(0);
        Assertions.assertTrue(passenger.willAlight());
    }

    @Test
    public void testGetTicket() {
        Assertions.assertNull(passenger.getTicket());
        passenger = new Passenger(new AdultTicket(), 3);
        Assertions.assertEquals(new AdultTicket(), passenger.getTicket());
    }

    @DisplayName("PassengerTest Class Teardown")
    @AfterAll
    public static void teardown() {
        passenger = null;
    }
}
