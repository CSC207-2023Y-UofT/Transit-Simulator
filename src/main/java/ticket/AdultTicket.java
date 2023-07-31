package ticket;

/**
 * AdultTicket is a concrete implementation of the AbstractNormalTicket class.
 * It represents a normal ticket that is specifically for adults.
 * This ticket has a predetermined type ("Adult"), price (3.35), and a lifetime (1 day).
 *
 * @see AbstractNormalTicket
 */
public class AdultTicket extends AbstractNormalTicket {

    /**
     * The lifetime of the ticket in milliseconds.
     * This is a constant value representing 1 day (24 hours).
     */
    public static final long TICKET_LIFETIME = 1000 * 60 * 60 * 24L;

    /**
     * Constructs a new AdultTicket object with the predetermined type, price and ticket lifetime.
     */
    public AdultTicket() {
        super("Adult", 3.35, TICKET_LIFETIME);
    }

}
