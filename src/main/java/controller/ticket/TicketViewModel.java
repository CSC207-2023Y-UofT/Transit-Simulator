package controller.ticket;

import interactor.ticket.BoughtTicket;

public class TicketViewModel {
    private BoughtTicket ticket;

    public TicketViewModel(BoughtTicket ticket) {
        this.ticket = ticket;
    }

    public void setTicket(BoughtTicket ticket) {
        this.ticket = ticket;
    }

    public BoughtTicket getTicket() {
        return ticket;
    }
}
