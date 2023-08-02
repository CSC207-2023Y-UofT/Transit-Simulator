package ticket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChildTicketTest {

    @Test
    public void testChildTicketType() {
        ChildTicket childTicket = new ChildTicket();
        String ticketType = childTicket.getTypeId();
        Assertions.assertEquals("Child", ticketType, "Ticket type should be 'Child'");
    }

    @Test
    public void testChildTicketPrice() {
        ChildTicket childTicket = new ChildTicket();
        double ticketPrice = childTicket.getPrice();
        Assertions.assertEquals(2.40, ticketPrice, 0.001, "Ticket price should be 2.40");
    }

    @Test
    public void testChildTicketExpiration() {
        ChildTicket childTicket = new ChildTicket();
        long currentTime = System.currentTimeMillis();
        long ticketExpiration = childTicket.getExpiry();
        Assertions.assertTrue(currentTime < ticketExpiration, "Ticket should not have expired yet");
    }

}
