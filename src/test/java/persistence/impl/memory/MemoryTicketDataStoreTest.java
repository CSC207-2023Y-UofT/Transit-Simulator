package persistence.impl.memory;

import entity.ticket.Ticket;
import entity.ticket.TicketType;
import org.junit.jupiter.api.Test;
import persistence.boundary.TicketDataStore;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link MemoryTicketDataStore}
 */
class MemoryTicketDataStoreTest {

    /**
     * Data store
     */
    private static final TicketDataStore STORE = new MemoryTicketDataStore();

    /**
     * Test adding a ticket
     */
    @Test
    void save() {
        Ticket ticket = new Ticket(TicketType.ADULT);
        STORE.save(ticket);
        assertTrue(STORE.existsById(ticket.getId()));
    }

    /**
     * Test finding a ticket
     */
    @Test
    void find() {
        Ticket ticket = new Ticket(TicketType.ADULT);
        STORE.save(ticket);
        assertTrue(STORE.find(ticket.getId()).isPresent());
        assertEquals(ticket, STORE.find(ticket.getId()).get());
    }

    /**
     * Test finding all tickets
     */
    @Test
    void findAll() {
        Ticket ticket = new Ticket(TicketType.ADULT);
        Ticket ticket2 = new Ticket(TicketType.ADULT);
        STORE.save(ticket);
        STORE.save(ticket2);
        assertEquals(2, STORE.findAll().size());
        assertTrue(STORE.findAll().contains(ticket));
        assertTrue(STORE.findAll().contains(ticket2));
    }

    /**
     * Test checking if a ticket exists
     */
    @Test
    void existsById() {
        Ticket ticket = new Ticket(TicketType.ADULT);
        STORE.save(ticket);
        assertTrue(STORE.existsById(ticket.getId()));
    }

    /**
     * Test deleting a ticket
     */
    @Test
    void delete() {
        Ticket ticket = new Ticket(TicketType.ADULT);
        STORE.save(ticket);
        assertTrue(STORE.existsById(ticket.getId()));
        STORE.delete(ticket.getId());
        assertFalse(STORE.existsById(ticket.getId()));
    }

    /**
     * Test deleting all tickets
     */
    @Test
    void deleteAll() {
        Ticket ticket = new Ticket(TicketType.ADULT);
        Ticket ticket2 = new Ticket(TicketType.ADULT);
        STORE.save(ticket);
        STORE.save(ticket2);
        assertTrue(STORE.existsById(ticket.getId()));
        assertTrue(STORE.existsById(ticket2.getId()));
        STORE.deleteAll();
        assertFalse(STORE.existsById(ticket.getId()));
        assertFalse(STORE.existsById(ticket2.getId()));
    }

    /**
     * Test deleting expired tickets
     */
    @Test
    void cleanExpiredTickets() {
        Ticket ticket = new Ticket(TicketType.ADULT);
        Ticket ticket2 = new Ticket(TicketType.ADULT);
        ticket.setCreatedAt(-9999999);
        ticket2.setActivated(true);
        ticket2.setExpiry(-9999999);
        STORE.save(ticket);
        STORE.save(ticket2);
        assertTrue(STORE.existsById(ticket.getId()));
        assertTrue(STORE.existsById(ticket2.getId()));
        STORE.cleanExpiredTickets();
        assertFalse(STORE.existsById(ticket.getId()));
        assertFalse(STORE.existsById(ticket2.getId()));
    }
}