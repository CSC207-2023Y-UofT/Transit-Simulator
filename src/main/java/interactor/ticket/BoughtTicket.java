package interactor.ticket;

import ticket.TicketType;

/**
 * Represents a purchased ticket in the system.
 * This class provides details related to a bought ticket including its cost, type, activation status, and expiry time.
 */
public class BoughtTicket {

    /** The cost of the bought ticket. */
    private final double cost;

    /** The type of the ticket. */
    private final TicketType type;

    /** The unique identifier for the ticket. */
    private final int ticketId;

    /** Indicates if the ticket has been activated. */
    private boolean activated;

    /** Timestamp indicating when the ticket will expire. */
    private long expiry;

    /**
     * Constructs a new BoughtTicket with the specified attributes.
     *
     * @param cost      The cost of the ticket.
     * @param type      The type of the ticket.
     * @param ticketId  The unique identifier for the ticket.
     * @param activated Indicates if the ticket has been activated.
     * @param expiry    Timestamp indicating when the ticket will expire.
     */
    public BoughtTicket(double cost, TicketType type, int ticketId,
                        boolean activated, long expiry) {
        this.cost = cost;
        this.type = type;
        this.ticketId = ticketId;
        this.activated = activated;
        this.expiry = expiry;
    }

    /**
     * Checks if the ticket has been activated.
     *
     * @return {@code true} if the ticket is activated, {@code false} otherwise.
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Retrieves the expiry timestamp of the ticket.
     *
     * @return The expiry timestamp.
     */
    public long getExpiry() {
        return expiry;
    }

    /**
     * Retrieves the cost of the ticket.
     *
     * @return The cost of the ticket.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Retrieves the type of the ticket.
     *
     * @return The type of the ticket.
     */
    public TicketType getType() {
        return type;
    }

    /**
     * Retrieves the unique identifier of the ticket.
     *
     * @return The ticket ID.
     */
    public int getTicketId() {
        return ticketId;
    }
}
