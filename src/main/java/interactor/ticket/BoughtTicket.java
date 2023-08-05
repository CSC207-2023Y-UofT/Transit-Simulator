package interactor.ticket;

import ticket.TicketType;

public class BoughtTicket {
    private final double cost;
    private final TicketType type;
    private final int ticketId;

    public BoughtTicket(double cost, TicketType type, int ticketId) {
        this.cost = cost;
        this.type = type;
        this.ticketId = ticketId;
    }

    public double getCost() {
        return cost;
    }

    public TicketType getType() {
        return type;
    }

    public int getTicketId() {
        return ticketId;
    }
}
