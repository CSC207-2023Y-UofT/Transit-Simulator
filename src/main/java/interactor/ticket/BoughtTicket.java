package interactor.ticket;

public class BoughtTicket {
    private final double cost;
    private final String type;
    private final int ticketId;

    public BoughtTicket(double cost, String type, int ticketId) {
        this.cost = cost;
        this.type = type;
        this.ticketId = ticketId;
    }

    public double getCost() {
        return cost;
    }

    public String getType() {
        return type;
    }

    public int getTicketId() {
        return ticketId;
    }
}
