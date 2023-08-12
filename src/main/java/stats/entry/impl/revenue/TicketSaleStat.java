package stats.entry.impl.revenue;

import entity.ticket.Ticket;
import stats.entry.impl.revenue.RevenueStat;

/**
 * This class represents a ticket sale statistic entry that implements the {@link RevenueStat} interface.
 * It encapsulates information about the ticket type and its price (which is considered as revenue in this context).
 */
public class TicketSaleStat implements RevenueStat {

    /**
     * The type of the ticket.
     */
    private final String ticketType;

    /**
     * The price of the ticket, representing the revenue.
     */
    private final double price;

    /**
     * Constructs a TicketSaleStat instance with the specified ticket.
     *
     * @param ticket The ticket for which the statistic is applicable.
     */
    public TicketSaleStat(Ticket ticket) {
        ticketType = ticket.getType().name();
        price = ticket.getPrice();
    }

    /**
     * Returns the price of the ticket, which represents the revenue.
     *
     * @return The price of the ticket as a double value.
     */
    @Override
    public double getRevenue() {
        return price;
    }
}
