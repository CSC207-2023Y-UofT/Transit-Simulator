package ticket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StudentTicketTest {

    @Test
    public void testStudentTicketType() {
        StudentTicket studentTicket = new StudentTicket();
        String ticketType = studentTicket.getTypeId();
        Assertions.assertEquals("Student", ticketType, "Ticket type should be 'Student'");
    }

    @Test
    public void testStudentTicketPrice() {
        StudentTicket studentTicket = new StudentTicket();
        double ticketPrice = studentTicket.getPrice();
        Assertions.assertEquals(2.35, ticketPrice, 0.001, "Ticket price should be 2.35");
    }

    @Test
    public void testStudentTicketExpiration() {
        StudentTicket studentTicket = new StudentTicket();
        long currentTime = System.currentTimeMillis();
        long ticketExpiration = studentTicket.getExpiry();
        Assertions.assertTrue(currentTime < ticketExpiration, "Ticket should not have expired yet");
    }
}
