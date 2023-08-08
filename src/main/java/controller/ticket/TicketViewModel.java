package controller.ticket;

import interactor.ticket.BoughtTicket;

/**
 * View model for a ticket view
 */
public class TicketViewModel {
    /**
     * The ticket
     */
    private BoughtTicket ticket;

    /**
     * Constructor
     */
    public TicketViewModel(BoughtTicket ticket) {
        this.ticket = ticket;
    }

    /**
     * Ticket setter
     */
    public void setTicket(BoughtTicket ticket) {
        this.ticket = ticket;
    }

    /**
     * Ticket getter
     */
    public BoughtTicket getTicket() {
        return ticket;
    }
}
