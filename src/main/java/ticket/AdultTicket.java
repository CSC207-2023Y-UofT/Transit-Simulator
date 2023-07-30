package ticket;

public class AdultTicket extends AbstractNormalTicket {
    public static final long TICKET_LIFETIME = 1000 * 60 * 60 * 24L; // 1 day

    public AdultTicket() {
        super("Adult", 3.35, TICKET_LIFETIME);
    }
}
