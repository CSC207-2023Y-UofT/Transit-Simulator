package presenter;

import ticket.TicketType;

import java.util.ArrayList;
import java.util.List;

public class PurchaseTicketViewModel {
    private final List<TicketType> ticketTypesList = new ArrayList<>();

    public void addTicket(TicketType type) {
        ticketTypesList.add(type);
    }

    public void removeTicket(TicketType type) {
        ticketTypesList.remove(type);
    }

    public List<TicketType> getTicketTypesList() {
        return ticketTypesList;
    }

}
