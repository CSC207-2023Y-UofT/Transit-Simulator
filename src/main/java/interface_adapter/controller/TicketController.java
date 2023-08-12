package interface_adapter.controller;

import app_business.dto.TicketDTO;
import app_business.boundary.ITicketInteractor;
import entity.ticket.TicketType;

import java.util.List;
import java.util.Optional;

/**
 * Controller, basically just a delegator to an ITicketInteractor instance
 */
public class TicketController {

    /**
     * The ticket interactor
     */
    private final ITicketInteractor interactor;

    /**
     * The constructor
     */
    public TicketController(ITicketInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Buys tickets
     * @param ticketTypes The types of tickets to buy
     * @return The list of bought tickets
     */
    public List<TicketDTO> buyTickets(List<TicketType> ticketTypes) {
        return interactor.buyTickets(ticketTypes);
    }

    /**
     * Activates a ticket
     *
     * @param ticketId The id of the ticket to activate
     */
    public void activateTicket(int ticketId) {
        interactor.activateTicket(ticketId);
    }

    /**
     * Gets a ticket
     * @param ticketId The id of the ticket to get
     * @return The ticket, or empty if the ticket was not found
     */
    public Optional<TicketDTO> getTicket(int ticketId) {
        return interactor.getTicket(ticketId);
    }

}
