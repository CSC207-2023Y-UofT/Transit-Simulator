package ticket;

import java.util.List;
import java.util.Optional;

public interface TicketDataStore {
    Optional<Ticket> getTicket(int id);
    void saveTicket(Ticket ticket);
    void removeTicket(int id);

    List<Ticket> getTickets();

    void cleanExpiredTickets();
}