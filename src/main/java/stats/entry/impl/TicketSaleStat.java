package stats.entry.impl;

import ticket.Ticket;

public class TicketSaleStat implements RevenueStat {
    private final String ticketType;
    private final double price;

    public TicketSaleStat(Ticket ticket) {
        ticketType = ticket.getTypeId();
        price = ticket.getPrice();
    }

    public String getTicketType() {
        return ticketType;
    }

    @Override
    public double getRevenue() {
        return price;
    }
}
