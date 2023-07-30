package ticket;

/**
 * StudentTicket is a concrete implementation of the AbstractNormalTicket class.
 * It represents a normal ticket that is specifically for students.
 * This ticket has a predetermined type ("Student"), price (2.35), and a lifetime (1 day).
 *
 * @see AbstractNormalTicket
 */
public class StudentTicket extends AbstractNormalTicket {

    /**
     * The lifetime of the ticket in milliseconds.
     * This is a constant value representing 1 day (24 hours).
     */
    public static final long TICKET_LIFETIME = 1000 * 60 * 60 * 24L;

    /**
     * Constructs a new StudentTicket object with the predetermined type, price and ticket lifetime.
     */
    public StudentTicket() {
        super("Student", 2.35, TICKET_LIFETIME);
    }

}