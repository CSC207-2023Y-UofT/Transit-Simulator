package ticket;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a data store for tickets.
 */
public interface TicketDataStore {

    /**
     * Returns the ticket with the given id.
     *
     * @param id the id of the ticket
     * @return the ticket with the given id
     */
    Optional<Ticket> getTicket(int id);

    /**
     * Saves the given ticket.
     *
     * @param ticket the ticket to save
     */
    void saveTicket(Ticket ticket);

    /**
     * Removes the ticket with the given id.
     *
     * @param id the id of the ticket to remove
     */
    void removeTicket(int id);

    /**
     * Returns a list of all tickets.
     *
     * @return a list of all tickets
     */
    List<Ticket> getTickets();

    /**
     * Removes all expired tickets.
     */
    void cleanExpiredTickets();
}
