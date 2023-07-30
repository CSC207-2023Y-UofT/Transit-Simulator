package ticket;

public class ChildTicket extends AbstractNormalTicket {
    private static final int MAX_AGE = 15;
    public static long TICKET_LIFETIME = 1000 * 60 * 60 * 24L; // 1 day


    public ChildTicket() {
        super("Child", 2.40, TICKET_LIFETIME);
    }
}

