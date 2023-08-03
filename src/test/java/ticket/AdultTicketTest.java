package ticket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdultTicketTest {

    @Test
    public void testAdultTicketType() {
        AdultTicket adultTicket = new AdultTicket();
        String ticketType = adultTicket.getTypeId();
        Assertions.assertEquals("Adult", ticketType, "Ticket type should be 'Adult'");
    }

    @Test
    public void testAdultTicketPrice() {
        AdultTicket adultTicket = new AdultTicket();
        double ticketPrice = adultTicket.getPrice();
        Assertions.assertEquals(3.35, ticketPrice, 0.001, "Ticket price should be 3.35");
    }

    @Test
    public void testAdultTicketExpiration() {
        AdultTicket adultTicket = new AdultTicket();
        long currentTime = System.currentTimeMillis();
        long ticketExpiration = adultTicket.getExpiry();
        Assertions.assertTrue(currentTime < ticketExpiration, "Ticket should not have expired yet");
    }
}
