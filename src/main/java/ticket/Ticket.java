package ticket;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Ticket is an abstract class that represents a general ticket.
 * Each ticket has an expiry time and abstract methods to get the type ID and price.
 * The expiry time is given at the creation of the ticket.
 * The type ID and price should be provided by the concrete classes that extend Ticket.
 */
public abstract class Ticket {

    /**
     * The expiry time of the ticket in milliseconds.
     */
    private final long expiry;

    private final int ticketId;

    /**
     * Constructs a new Ticket object with the given expiry time.
     *
     * @param expiry the expiry time of the ticket in milliseconds
     */
    public Ticket(long expiry) {
        this.expiry = expiry;
        ticketId = ThreadLocalRandom.current().nextInt(999999999);
    }

    /**
     * Returns the expiry time of the ticket.
     *
     * @return the expiry time of the ticket in milliseconds
     */
    public long getExpiry() {
        return expiry;
    }

    /**
     * Returns this ticket's id.
     */
    public int getTicketId() {
        return ticketId;
    }

    /**
     * Returns the ID of the type of the ticket.
     * This method should be implemented by the concrete classes that extend Ticket.
     *
     * @return the ID of the type of the ticket
     */
    public abstract String getTypeId();

    /**
     * Returns the price of the ticket.
     * This method should be implemented by the concrete classes that extend Ticket.
     *
     * @return the price of the ticket
     */
    public abstract double getPrice();
}