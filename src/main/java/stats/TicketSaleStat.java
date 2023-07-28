package stats;

import ticket.Ticket;

/**
 * Event for a ticket sale.
 */
public class TicketSaleStat implements RevenueStat {

    /**
     * The ticket that was sold.
     */
    private final Ticket ticket;

    /**
     * Create a new TicketSaleEvent.
     */
    public TicketSaleStat(Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * Return the ticket that was sold.
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Return the revenue.
     */
    @Override
    public double getRevenue() {
        return ticket.getPrice();
    }
}
