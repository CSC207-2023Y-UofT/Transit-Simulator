package ticket;

public class StudentTicket extends AbstractNormalTicket {
    public static final long TICKET_LIFETIME = 1000 * 60 * 60 * 24L; // 1 day

    public StudentTicket() {
        super("Student", 2.35, TICKET_LIFETIME);
    }
}