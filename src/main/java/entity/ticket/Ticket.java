package entity.ticket;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Ticket is a class that represents a general ticket.
 * Each ticket has an expiry time and abstract methods to get the type ID and price.
 * The expiry time is given at the creation of the ticket.
 * The type of ticket and price should be provided by the TicketType at instantiation.
 */
public class Ticket {

    public static final long HARD_EXPIRY_TIME = 1000 * 60 * 60 * 24; // 1 day

    /**
     * The type of the ticket.
     */
    private final TicketType type;

    /**
     * The expiry time of the ticket in milliseconds.
     */
    private long expiry = -1;

    /**
     * The time when the ticket was created in milliseconds.
     */
    private long createdAt = System.currentTimeMillis();

    /**
     * The id of the ticket
     */
    private final int id;

    /**
     * Whether the ticket is activated or not.
     */
    private boolean activated = false;

    /**
     * Constructs a new Ticket object. The ticket's id is given as a parameter.
     */
    public Ticket(int id, TicketType type) {
        this.type = type;
        this.id = id;
    }

    /**
     * Constructs a new Ticket object. The ticket's id is randomly generated.
     * The ticket's type is given as a parameter.
     */
    public Ticket(TicketType type) {
        this(ThreadLocalRandom.current().nextInt(999999999), type);
    }

    /**
     * Returns this ticket's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the type of the ticket.
     *
     * @return the type of the ticket
     */
    public TicketType getType() {
        return type;
    }

    /**
     * Returns the price of the ticket.
     * This method should be implemented by the concrete classes that extend Ticket.
     *
     * @return the price of the ticket
     */
    public double getPrice() {
        return getType().getPrice();
    }

    /**
     * Returns whether the ticket is activated or not.
     *
     * @return whether the ticket is activated or not
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Sets whether the ticket is activated or not.
     *
     * @param activated whether the ticket is activated or not
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * Returns the expiry time of the ticket, or -1 if the ticket is not activated.
     *
     * @return the expiry time of the ticket in milliseconds, or -1 if the ticket is not activated
     */
    public long getExpiry() {
        return expiry;
    }

    /**
     * Sets the expiry time of the ticket.
     *
     * @param expiry the expiry time of the ticket in milliseconds
     */
    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    /**
     * Returns the time when the ticket was created.
     *
     * @return the time when the ticket was created in milliseconds
     */
    public long getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the time when the ticket was created.
     *
     * @param createdAt the time when the ticket was created in milliseconds
     */
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Activates the ticket.
     */
    public void activate() {
        activated = true;
        this.expiry = System.currentTimeMillis() + type.getLifetime();
    }

    /**
     * Checks whether this ticket is equal to obj.
     *
     * @param obj The object to compare to.
     * @return True iff obj is a Ticket with the same id as this ticket.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ticket) {
            Ticket other = (Ticket) obj;
            return other.getId() == getId();
        }
        return false;
    }

    public boolean isExpired() {
        long now = System.currentTimeMillis();
        if (expiry != -1 && now > expiry) return true;
        return createdAt != -1 && now > createdAt + Ticket.HARD_EXPIRY_TIME;
    }

}