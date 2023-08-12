package app_business.dto;

import entity.ticket.TicketType;

/**
 * Represents a ticket that has been bought by a user.
 */
public class TicketDTO {

    /**
     * The cost of the ticket.
     */
    private final double cost;

    /**
     * The type of the ticket.
     */
    private final TicketType type;

    /**
     * The id of the ticket.
     */
    private final int ticketId;

    /**
     * Whether the ticket has been activated.
     */
    private final boolean activated;

    /**
     * The expiry time of the ticket.
     */
    private final long expiry;

    /**
     * Constructs a new BoughtTicket with the given cost, type, ticketId, activated and expiry.
     *
     * @param cost      The cost of the ticket.
     * @param type      The type of the ticket.
     * @param ticketId  The id of the ticket.
     * @param activated Whether the ticket has been activated.
     * @param expiry    The expiry time of the ticket in ms.
     */
    public TicketDTO(double cost, TicketType type, int ticketId,
                     boolean activated, long expiry) {
        this.cost = cost;
        this.type = type;
        this.ticketId = ticketId;
        this.activated = activated;
        this.expiry = expiry;
    }

    /**
     * Gets whether the ticket has been activated.
     *
     * @return Whether the ticket has been activated.
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Gets the expiry time of the ticket.
     *
     * @return The expiry time of the ticket.
     */
    public long getExpiry() {
        return expiry;
    }

    /**
     * Gets the cost of the ticket.
     *
     * @return The cost of the ticket.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Gets the type of the ticket.
     *
     * @return The type of the ticket.
     */
    public TicketType getType() {
        return type;
    }

    /**
     * Gets the id of the ticket.
     *
     * @return The id of the ticket.
     */
    public int getTicketId() {
        return ticketId;
    }

    /**
     * Checks whether this ticket is equal to another object.
     *
     * @param obj The object to compare to.
     * @return Whether this ticket is equal to the other object.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TicketDTO) {
            TicketDTO other = (TicketDTO) obj;
            return other.getTicketId() == this.getTicketId() &&
                    other.getType().equals(this.getType()) &&
                    other.getCost() == this.getCost() &&
                    other.isActivated() == this.isActivated() &&
                    other.getExpiry() == this.getExpiry();
        }
        return false;
    }
}
