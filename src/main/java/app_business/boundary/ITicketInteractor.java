package app_business.boundary;

import app_business.dto.TicketDTO;
import entity.ticket.TicketType;

import java.util.List;
import java.util.Optional;

/**
 * Input boundary for ticket interactor
 */
public interface ITicketInteractor {

    /**
     * Buys tickets
     *
     * @param ticketTypes The types of tickets to buy
     * @return The list of bought tickets
     */
    List<TicketDTO> buyTickets(List<TicketType> ticketTypes);

    /**
     * Gets a ticket
     *
     * @param ticketId The id of the ticket to get
     * @return The ticket, or empty if the ticket was not found
     */
    Optional<TicketDTO> getTicket(int ticketId);

    /**
     * Activates a ticket
     *
     * @param ticketId The id of the ticket to activate
     * @return The activated ticket, or empty if the ticket was not found
     * @apiNote This method will still return the bought ticket if it is already active
     */
    Optional<TicketDTO> activateTicket(int ticketId);

    /**
     * Remove expired tickets.
     */
    void cleanTickets();
}
