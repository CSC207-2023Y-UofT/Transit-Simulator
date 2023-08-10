package controller.ticket;

import interactor.ticket.TicketDTO;

/**
 * View model for a ticket view
 */
public class TicketViewModel {

    /**
     * The ticket
     */
    private TicketDTO ticket;

    /**
     * Constructor
     */
    public TicketViewModel(TicketDTO ticket) {
        this.ticket = ticket;
    }

    /**
     * Ticket setter
     */
    public void setTicket(TicketDTO ticket) {
        this.ticket = ticket;
    }

    /**
     * Ticket getter
     */
    public TicketDTO getTicket() {
        return ticket;
    }
}
