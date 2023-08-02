package ticket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SeniorTicketTest {

    @Test
    public void testSeniorTicketType() {
        SeniorTicket seniorTicket = new SeniorTicket();
        String ticketType = seniorTicket.getTypeId();
        Assertions.assertEquals("Senior", ticketType, "Ticket type should be 'Senior'");
    }

    @Test
    public void testSeniorTicketPrice() {
        SeniorTicket seniorTicket = new SeniorTicket();
        double ticketPrice = seniorTicket.getPrice();
        Assertions.assertEquals(2.30, ticketPrice, 0.001, "Ticket price should be 2.30");
    }

    @Test
    public void testSeniorTicketExpiration() {
        SeniorTicket seniorTicket = new SeniorTicket();
        long currentTime = System.currentTimeMillis();
        long ticketExpiration = seniorTicket.getExpiry();
        Assertions.assertTrue(currentTime < ticketExpiration, "Ticket should not have expired yet");
    }

    @Test
    public void testSeniorTicketMinAge() {
        int minAge = SeniorTicket.MIN_AGE;
        Assertions.assertEquals(65, minAge, "Minimum age for senior ticket should be 65");
    }
}
