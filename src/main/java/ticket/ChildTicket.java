package ticket;

/**
 * ChildTicket is a concrete implementation of the AbstractNormalTicket class.
 * It represents a normal ticket that is specifically for children.
 * This ticket has a predetermined type ("Child"), price (2.40), and a lifetime (1 day).
 * The class also maintains a maximum age limit for the child (MAX_AGE = 15).
 *
 * @see AbstractNormalTicket
 */
public class ChildTicket extends AbstractNormalTicket {

    /**
     * The maximum age limit for which this ticket can be used.
     */
    private static final int MAX_AGE = 15;

    /**
     * The lifetime of the ticket in milliseconds.
     * This is a constant value representing 1 day (24 hours).
     */
    public static final long TICKET_LIFETIME = 1000 * 60 * 60 * 24L;
    public static double PRICE = 2.40;

    /**
     * Constructs a new ChildTicket object with the predetermined type, price and ticket lifetime.
     */
    public ChildTicket() {
        super("Child", PRICE, TICKET_LIFETIME);
    }

    public void updatePrice(double price){
        ChildTicket.PRICE = price;
    }

}