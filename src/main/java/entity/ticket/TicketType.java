package entity.ticket;

/**
 * Represents the different ticket types
 */
public enum TicketType {

    CHILD(2.40, 1000 * 60 * 60 * 2),
    ADULT(3.35, 1000 * 60 * 60 * 2),
    STUDENT(2.35, 1000 * 60 * 60 * 2),
    SENIOR(2.30, 1000 * 60 * 60 * 2);

    /** The price of the ticket. */
    private final double price;

    /** The lifetime of the ticket in milliseconds. */
    private final long lifetime;

    /**
     * Constructs a new TicketType with the given price and lifetime.
     *
     * @param price the price of the ticket
     * @param lifetime the lifetime of the ticket in milliseconds
     */
    TicketType(double price, long lifetime) {
        this.price = price;
        this.lifetime = lifetime;
    }

    /** Returns the price of the ticket. */
    public double getPrice() {
        return price;
    }

    /** Returns the lifetime of the ticket in milliseconds. */
    public long getLifetime() {
        return lifetime;
    }
}
