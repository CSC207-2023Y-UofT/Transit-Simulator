package interactor.ticket;

import ticket.TicketType;

public class BoughtTicket {
    private final double cost;
    private final TicketType type;
    private final int ticketId;
    private boolean activated;
    private long expiry;

    public BoughtTicket(double cost, TicketType type, int ticketId,
                        boolean activated, long expiry) {
        this.cost = cost;
        this.type = type;
        this.ticketId = ticketId;
        this.activated = activated;
        this.expiry = expiry;
    }

    public boolean isActivated() {
        return activated;
    }

    public long getExpiry() {
        return expiry;
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
