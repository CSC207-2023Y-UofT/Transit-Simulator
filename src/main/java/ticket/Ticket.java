package ticket;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Ticket is an abstract class that represents a general ticket.
 * Each ticket has an expiry time and abstract methods to get the type ID and price.
 * The expiry time is given at the creation of the ticket.
 * The type ID and price should be provided by the concrete classes that extend Ticket.
 */
public class Ticket {

    private final TicketType type;
    /**
     * The expiry time of the ticket in milliseconds.
     */
    private long expiry = -1;

    /**
     * The id of the ticket
     */
    private final int ticketId;

    /**
     * Whether the ticket is activated or not.
     */
    private boolean activated = false;

    /**
     * Constructs a new Ticket object. The ticket's id is randomly generated.
     * The ticket's type is given as a parameter.
     */
    public Ticket(TicketType type) {
        this.type = type;
        ticketId = ThreadLocalRandom.current().nextInt(999999999);
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
        return ticketId;
    }

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
}