package ticket;

/**
 * SeniorTicket is a concrete implementation of the AbstractNormalTicket class.
 * It represents a normal ticket that is specifically for seniors.
 * This ticket has a predetermined type ("Senior"), price (2.30), and a lifetime (1 day).
 * The class also maintains a minimum age limit for the senior (MIN_AGE = 65).
 *
 * @see AbstractNormalTicket
 */
public class SeniorTicket extends AbstractNormalTicket {

    /**
     * The minimum age limit for which this ticket can be used.
     */
    public static final int MIN_AGE = 65;

    /**
     * The lifetime of the ticket in milliseconds.
     * This is a constant value representing 1 day (24 hours).
     */
    public static final long TICKET_LIFETIME = 1000 * 60 * 60 * 24L;

    /**
     * The price of the SeniorTicket.
     */
    public static final double PRICE = 2.30;

    /**
     * Constructs a new SeniorTicket object with the predetermined type, price and ticket lifetime.
     */
    public SeniorTicket() {
        super("Senior", PRICE, TICKET_LIFETIME);
    }

}