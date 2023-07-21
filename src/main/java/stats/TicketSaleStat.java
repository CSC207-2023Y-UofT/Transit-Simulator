package stats;

public class TicketSaleStat implements RevenueStat {

    private final Ticket ticket;

    public TicketSaleStat(Ticket ticket) {
        this.ticket = ticket;
    }


    public Ticket getTicket() {
        return ticket;
    }

    @Override
    public double getRevenue() {
        return ticket.getPrice();
    }
}
