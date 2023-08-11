package persistence.boundary;

import entity.ticket.Ticket;

/**
 * This interface represents a data store for tickets.
 */
public interface TicketDataStore extends IntIndexedDataStore<Ticket> {

    /**
     * Removes all expired tickets.
     */
    void cleanExpiredTickets();
}
