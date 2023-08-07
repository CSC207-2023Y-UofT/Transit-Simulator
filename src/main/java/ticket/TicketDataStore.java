package ticket;

import java.util.List;
import java.util.Optional;

/**
 * Defines the methods for a data store that handles ticket operations.
 * This interface provides a set of standard operations to interact with
 * tickets including retrieval, saving, deletion, and cleaning expired tickets.
 */
public interface TicketDataStore {

    /**
     * Retrieves a ticket from the data store based on its ID.
     *
     * @param id The unique ID of the ticket to retrieve.
     * @return An {@code Optional} containing the ticket if found, or an empty {@code Optional} if not.
     */
    Optional<Ticket> getTicket(int id);

    /**
     * Saves a ticket to the data store. If the ticket already exists,
     * it should update the existing record. Otherwise, it should create a new one.
     *
     * @param ticket The ticket object to save.
     */
    void saveTicket(Ticket ticket);

    /**
     * Removes a ticket from the data store based on its ID.
     *
     * @param id The unique ID of the ticket to remove.
     */
    void removeTicket(int id);

    /**
     * Retrieves a list of all tickets present in the data store.
     *
     * @return A list of all tickets.
     */
    List<Ticket> getTickets();

    /**
     * Removes all expired tickets from the data store.
     * An expired ticket is one where its expiry time is less than the current time.
     */
    void cleanExpiredTickets();
}
