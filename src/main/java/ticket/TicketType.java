package ticket;

/**
 * Represents the different ticket types
 */
public enum TicketType {

    CHILD(0.0, 1000 * 60 * 60 * 2),
    ADULT(3.35, 1000 * 60 * 60 * 2),
    STUDENT(2.35, 1000 * 60 * 60 * 2),
    SENIOR(2.30, 1000 * 60 * 60 * 2);

    private final double price;
    private final long lifetime;

    TicketType(double price, long lifetime) {
        this.price = price;
        this.lifetime = lifetime;
    }

    public double getPrice() {
        return price;
    }

    public long getLifetime() {
        return lifetime;
    }
}
