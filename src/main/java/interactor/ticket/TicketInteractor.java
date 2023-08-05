package interactor.ticket;

import stats.entry.impl.TicketSaleStat;
import stats.persistence.StatDataController;
import ticket.*;

import java.util.ArrayList;
import java.util.List;

public class TicketInteractor {
    private final StatDataController stats;

    public TicketInteractor(StatDataController stats) {
        this.stats = stats;
    }

    public List<BoughtTicket> buyTicket(int childTickets,
                                        int adultTickets,
                                        int seniorTickets,
                                        int studentTickets) {

        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < childTickets; i++) {
            tickets.add(new ChildTicket());
        }

        for (int i = 0; i < adultTickets; i++) {
            tickets.add(new AdultTicket());
        }

        for (int i = 0; i < seniorTickets; i++) {
            tickets.add(new SeniorTicket());
        }

        for (int i = 0; i < studentTickets; i++) {
            tickets.add(new StudentTicket());
        }

        for (Ticket ticket : tickets) {
            TicketSaleStat saleStat = new TicketSaleStat(ticket);
            stats.record(saleStat);
        }

        List<BoughtTicket> response = new ArrayList<>();

        for (Ticket ticket : tickets) {
            BoughtTicket boughtTicket = new BoughtTicket(
                    ticket.getPrice(),
                    ticket.getTypeId(),
                    ticket.getTicketId()
            );
            response.add(boughtTicket);
        }

        return response;
    }

}
