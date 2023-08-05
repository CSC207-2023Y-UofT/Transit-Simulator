package interactor.ticket;

import ticket.TicketType;

import java.util.List;

/**
 * Input boundary for ticket interactor
 */
public interface ITicketInteractor {

    List<BoughtTicket> buyTickets(List<TicketType> ticketTypes);

    BoughtTicket getTicket(int ticketId);
}
