package ticket;


public class SeniorTicket extends AbstractNormalTicket {
    public static final int MIN_AGE = 65;
    public static final long TICKET_LIFETIME = 1000 * 60 * 60 * 24L; // 1 day

    public SeniorTicket() {
        super("Senior", 2.30, TICKET_LIFETIME);
    }
}