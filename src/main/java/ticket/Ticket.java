package ticket;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Ticket is an abstract class that represents a general ticket.
 * Each ticket has an expiry time and abstract methods to get the type ID and price.
 * The expiry time is given at the creation of the ticket.
 * The type ID and price should be provided by the concrete classes that extend Ticket.
 */
public class Ticket {

    /**
     * The type of the ticket.
     */
    private final TicketType type;
    /**
     * The expiry time of the ticket in milliseconds.
     */
    private long expiry = -1;

    /**
     * The time the ticket was created in milliseconds.
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
     * Constructs a new Ticket object. The ticket's id is randomly generated.
     * The ticket's type is given as a parameter.
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
     * Returns the expiry time of the ticket, or -1 if the ticket is not activated.
     *
     * @return the expiry time of the ticket in milliseconds, or -1 if the ticket is not activated
     */
    public long getExpiry() {
        return expiry;
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
     * Checks if the ticket is activated.
     *
     * @return True if activated, false otherwise.
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Sets the ticket's activation status.
     *
     * @param activated The activation status to set.
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * Sets the expiry time of the ticket.
     *
     * @param expiry The expiry time in milliseconds.
     */
    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    /**
     * Sets the creation time of the ticket.
     *
     * @param createdAt The creation time in milliseconds.
     */
    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the creation time of the ticket.
     *
     * @return The creation time in milliseconds.
     */
    public long getCreatedAt() {
        return createdAt;
    }

    /**
     * Activates the ticket and sets its expiry time based on its type's lifetime.
     */
    public void activate() {
        activated = true;
        this.expiry = System.currentTimeMillis() + type.getLifetime();
    }

    /**
     * Compares this ticket to another object for equality.
     *
     * @param obj The object to compare to.
     * @return True if the object is a Ticket with the same ID, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ticket) {
            Ticket other = (Ticket) obj;
            return other.getId() == getId();
        }
        return false;
    }

}