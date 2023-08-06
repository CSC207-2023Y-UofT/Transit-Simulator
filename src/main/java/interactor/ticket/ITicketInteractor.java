package interactor.ticket;

import ticket.TicketType;

import java.util.List;
import java.util.Optional;

/**
 * Input boundary for ticket interactor
 */
public interface ITicketInteractor {

    List<BoughtTicket> buyTickets(List<TicketType> ticketTypes);

    Optional<BoughtTicket> getTicket(int ticketId);

    Optional<BoughtTicket> activateTicket(int ticketId);

}
