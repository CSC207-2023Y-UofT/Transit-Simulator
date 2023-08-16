package persistence.impl;

import persistence.boundary.TicketDataStore;
import persistence.impl.file.JsonTicketDataStore;
import entity.ticket.Ticket;
import entity.ticket.TicketType;
import persistence.DataStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.AsyncWriteIOProvider;
import util.DeflateCompressionProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("BlockingMethodInNonBlockingContext")
class JsonTicketDataStoreTest {

    private static TicketDataStore data;
    private static Ticket ticket;


    /**
     * Utility method to delete a directory recursively.
     * @param directory The directory to delete.
     */
    private static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File f : files)
                    deleteDirectory(f);
            }
        }
        try {
            Files.delete(directory.toPath());
        } catch (IOException ignored) {}
    }

    @BeforeAll
    public static void setUp() {

        DataStorage.init(
                new AsyncWriteIOProvider(),
                new DeflateCompressionProvider()
        );

        File directory = new File("test-tickets");
        deleteDirectory(directory);

        data = new JsonTicketDataStore(directory);
        ticket = new Ticket(1, TicketType.ADULT);
        data.save(ticket);
    }

    @Test
    void getTicket() {
        Ticket copy = data.find(1).orElseThrow();
        assertEquals(ticket.getId(), copy.getId());
        assertEquals(ticket.getCreatedAt(), copy.getCreatedAt());
        assertEquals(ticket.getType(), copy.getType());
        assertEquals(ticket.isActivated(), copy.isActivated());
        assertEquals(ticket.getExpiry(), copy.getExpiry());
    }

    @Test
    void saveTicket() {
        Ticket ticket1 = new Ticket(2, TicketType.CHILD);
        data.save(ticket1);
    }

    @Test
    void removeTicket() {

        Ticket ticket2 = new Ticket(3, TicketType.SENIOR);

        data.save(ticket2);
        assertTrue(data.find(3).isPresent());

        data.delete(3);

        assertFalse(data.find(3).isPresent());

    }

    @Test
    void getTickets() {
        data.save(new Ticket(4, TicketType.CHILD));
        data.save(new Ticket(5, TicketType.SENIOR));
        data.save(new Ticket(6, TicketType.ADULT));
        data.save(new Ticket(7, TicketType.CHILD));
        data.save(new Ticket(8, TicketType.SENIOR));
        data.save(new Ticket(9, TicketType.ADULT));

        assertTrue(data.findAll().size() >= 6);
    }

    @Test
    void cleanExpiredTickets() {

        Ticket expired = new Ticket(10, TicketType.CHILD);
        expired.setExpiry(System.currentTimeMillis() - 1000);
        expired.setActivated(true);

        data.save(expired);

        assertTrue(data.find(10).isPresent());

        data.cleanExpiredTickets();

        assertFalse(data.find(10).isPresent());
    }

    @Test
    public void testDelete() {
        Ticket ticket = new Ticket(1, TicketType.ADULT);
        data.save(ticket);
        assert data.existsById(1);
        data.delete(1);
        assert !data.existsById(1);
    }
}