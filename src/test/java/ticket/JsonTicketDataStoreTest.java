package ticket;

import persistence.impl.JsonTicketDataStore;
import entity.ticket.Ticket;
import entity.ticket.TicketType;
import persistence.DataStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.AsyncWriteIOProvider;
import util.DeflateCompressionProvider;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class JsonTicketDataStoreTest {

    private static JsonTicketDataStore data;
    private static Ticket ticket;

    @BeforeAll
    public static void setUp() {

        DataStorage.init(
                new AsyncWriteIOProvider(),
                new DeflateCompressionProvider()
        );

        data = new JsonTicketDataStore(new File("test-tickets"));
        ticket = new Ticket(1, TicketType.ADULT);
        data.saveTicket(ticket);
    }

    @Test
    void getTicket() {
        Ticket copy = data.getTicket(1).orElseThrow();
        assertEquals(ticket.getId(), copy.getId());
        assertEquals(ticket.getCreatedAt(), copy.getCreatedAt());
        assertEquals(ticket.getType(), copy.getType());
        assertEquals(ticket.isActivated(), copy.isActivated());
        assertEquals(ticket.getExpiry(), copy.getExpiry());
    }

    @Test
    void saveTicket() {
        Ticket ticket1 = new Ticket(2, TicketType.CHILD);
        data.saveTicket(ticket1);
    }

    @Test
    void removeTicket() {

        Ticket ticket2 = new Ticket(3, TicketType.SENIOR);

        data.saveTicket(ticket2);
        assertTrue(data.getTicket(3).isPresent());

        data.removeTicket(3);
        assertFalse(data.getTicket(3).isPresent());

    }

    @Test
    void getTickets() {
        data.saveTicket(new Ticket(4, TicketType.CHILD));
        data.saveTicket(new Ticket(5, TicketType.SENIOR));
        data.saveTicket(new Ticket(6, TicketType.ADULT));
        data.saveTicket(new Ticket(7, TicketType.CHILD));
        data.saveTicket(new Ticket(8, TicketType.SENIOR));
        data.saveTicket(new Ticket(9, TicketType.ADULT));

        assertTrue(data.getTickets().size() >= 6);
    }

    @Test
    void cleanExpiredTickets() {

        Ticket expired = new Ticket(10, TicketType.CHILD);
        expired.setExpiry(System.currentTimeMillis() - 1000);
        expired.setActivated(true);

        data.saveTicket(expired);

        assertTrue(data.getTicket(10).isPresent());

        data.cleanExpiredTickets();

        assertFalse(data.getTicket(10).isPresent());
    }
}