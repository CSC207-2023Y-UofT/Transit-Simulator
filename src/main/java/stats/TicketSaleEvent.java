package stats;

public class TicketSaleEvent implements StatEntry{

    private final Ticket ticket;

    public TicketSaleEvent(Ticket ticket) {
        this.ticket = ticket;
    }


    public Ticket getTicket() {
        return ticket;
    }
}
