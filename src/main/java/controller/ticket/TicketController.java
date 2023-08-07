package controller.ticket;

import interactor.ticket.BoughtTicket;
import interactor.ticket.ITicketInteractor;
import ticket.TicketType;

import java.util.List;
import java.util.Optional;

/**
 * Controller class responsible for managing ticket-related operations.
 * It acts as a mediator between the view and the underlying ticket interactor.
 */
public class TicketController {

    /** The interactor used for ticket operations. */
    private final ITicketInteractor interactor;

    /**
     * Constructs a new instance of the TicketController with the provided ticket interactor.
     *
     * @param interactor The interactor responsible for ticket operations.
     */
    public TicketController(ITicketInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Initiates the process to buy a list of tickets.
     *
     * @param ticketTypes A list of {@link TicketType} representing the types of tickets to buy.
     * @return A list of {@link BoughtTicket} representing the bought tickets.
     */
    public List<BoughtTicket> buyTickets(List<TicketType> ticketTypes) {
        return interactor.buyTickets(ticketTypes);
    }

    /**
     * Activates a ticket with the given ticket ID.
     *
     * @param ticketId The ID of the ticket to activate.
     * @return An {@link Optional} containing the activated {@link BoughtTicket} if successful, or empty otherwise.
     */
    public Optional<BoughtTicket> activateTicket(int ticketId) {
        return interactor.activateTicket(ticketId);
    }

    /**
     * Retrieves details of a ticket with the given ticket ID.
     *
     * @param ticketId The ID of the ticket to retrieve.
     * @return An {@link Optional} containing the {@link BoughtTicket} if found, or empty otherwise.
     */
    public Optional<BoughtTicket> getTicket(int ticketId) {
        return interactor.getTicket(ticketId);
    }

}
