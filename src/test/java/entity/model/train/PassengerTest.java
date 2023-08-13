package entity.model.train;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import entity.ticket.Ticket;
import entity.ticket.TicketType;

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
        Assertions.assertFalse(passenger.shouldAlight());
        passenger.setStationsToTravel(0);
        Assertions.assertTrue(passenger.shouldAlight());
        passenger.setStationsToTravel(-1);
        Assertions.assertTrue(passenger.shouldAlight());
    }

    @Test
    public void testGetTicket() {
        Ticket ticket = new Ticket(TicketType.ADULT);
        passenger = new Passenger(ticket, 3);
        Assertions.assertEquals(ticket, passenger.getTicket());
    }

    @DisplayName("PassengerTest Class Teardown")
    @AfterAll
    public static void teardown() {
        passenger = null;
    }
}
