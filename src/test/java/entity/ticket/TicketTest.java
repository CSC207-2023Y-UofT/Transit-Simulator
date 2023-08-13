package entity.ticket;

import org.junit.jupiter.api.*;

import java.time.Duration;

public class TicketTest {
    Ticket ticket;

    @Test
    public void testTicket_OneParam() {
        ticket = new Ticket(TicketType.ADULT);
        Assertions.assertTrue(0 < ticket.getId() && ticket.getId() < 999999999);
        Assertions.assertEquals(TicketType.ADULT, ticket.getType());
    }

    @Test
    public void testTicket_TwoParam() {
        ticket = new Ticket(123, TicketType.ADULT);
        Assertions.assertEquals(123, ticket.getId());
        Assertions.assertEquals(TicketType.ADULT, ticket.getType());
    }

    @Test
    public void testGetId() {
        ticket = new Ticket(TicketType.ADULT);
        Assertions.assertInstanceOf(Integer.class, ticket.getId());

        ticket = new Ticket(123, TicketType.ADULT);
        Assertions.assertEquals(123, ticket.getId());
    }

    @Test
    public void testGetType() {
        ticket = new Ticket(TicketType.ADULT);
        Assertions.assertEquals(TicketType.ADULT, ticket.getType());

        ticket = new Ticket(123, TicketType.CHILD);
        Assertions.assertEquals(TicketType.CHILD, ticket.getType());

        ticket = new Ticket(123, TicketType.ADULT);
        Assertions.assertEquals(TicketType.ADULT, ticket.getType());
    }

    @Test
    public void testGetPrice() {
        ticket = new Ticket(TicketType.ADULT);
        Assertions.assertEquals(TicketType.ADULT.getPrice(), ticket.getPrice());

        ticket = new Ticket(TicketType.CHILD);
        Assertions.assertEquals(TicketType.CHILD.getPrice(), ticket.getPrice());

        ticket = new Ticket(TicketType.SENIOR);
        Assertions.assertEquals(TicketType.SENIOR.getPrice(), ticket.getPrice());

        ticket = new Ticket(TicketType.STUDENT);
        Assertions.assertEquals(TicketType.STUDENT.getPrice(), ticket.getPrice());
    }

    @Test
    public void test_IsActivated_SetActivated() {
        ticket = new Ticket(TicketType.ADULT);
        Assertions.assertFalse(ticket.isActivated());

        ticket.setActivated(true);
        Assertions.assertTrue(ticket.isActivated());

        ticket.setActivated(false);
        Assertions.assertFalse(ticket.isActivated());
    }

    @Test
    public void test_GetExpiry_SetExpiry() {
        ticket = new Ticket(TicketType.ADULT);
        Assertions.assertEquals(-1, ticket.getExpiry());  // Not activated; expiry is -1

        ticket.setExpiry(123);
        Assertions.assertEquals(123, ticket.getExpiry());

        ticket.setExpiry(-1);
        Assertions.assertEquals(-1, ticket.getExpiry());
    }

    @Test
    public void test_GetCreatedAt_SetCreatedAt() {
        Assertions.assertTimeout(Duration.ofSeconds(1), () ->
            ticket = new Ticket(TicketType.ADULT)
        );
        long currMillis = System.currentTimeMillis();
        Assertions.assertTrue(currMillis - ticket.getCreatedAt() <= 1000);
        ticket.setCreatedAt(currMillis);
        Assertions.assertEquals(currMillis, ticket.getCreatedAt());
    }

    @Test
    public void testActivate() {
        ticket = new Ticket(TicketType.ADULT);
        Assertions.assertFalse(ticket.isActivated());
        Assertions.assertEquals(-1, ticket.getExpiry());

        ticket.activate();
        Assertions.assertTrue(ticket.isActivated());
        Assertions.assertTrue(ticket.getExpiry() > System.currentTimeMillis());
    }

    @Test
    public void testEquals() {
        ticket = new Ticket(1000000001, TicketType.ADULT);
        Ticket ticket2 = new Ticket(TicketType.ADULT);
        Assertions.assertNotEquals(ticket, ticket2);
        Assertions.assertNotEquals(ticket2, ticket);
        ticket2 = new Ticket(1000000001, TicketType.ADULT);
        Assertions.assertEquals(ticket, ticket2);
        Assertions.assertEquals(ticket2, ticket);
    }
}

