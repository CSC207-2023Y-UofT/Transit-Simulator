package controller;

import interactor.ticket.BoughtTicket;
import interactor.ticket.ITicketInteractor;
import ticket.TicketType;

import java.util.List;
import java.util.Optional;

public class TicketController {

    private final ITicketInteractor interactor;

    public TicketController(ITicketInteractor interactor) {
        this.interactor = interactor;
    }

    public List<BoughtTicket> buyTickets(List<TicketType> ticketTypes) {
        return interactor.buyTickets(ticketTypes);
    }

    public Optional<BoughtTicket> activateTicket(int ticketId) {
        return interactor.activateTicket(ticketId);
    }

    public Optional<BoughtTicket> getTicket(int ticketId) {
        return interactor.getTicket(ticketId);
    }

}
