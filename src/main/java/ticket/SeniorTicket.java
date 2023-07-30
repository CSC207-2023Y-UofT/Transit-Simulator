package ticket;


public class SeniorTicket extends Ticket {
    public static final int MIN_AGE = 65;
    public static final long TICKET_LIFETIME = 1000 * 60 * 60 * 24L; // 1 day

    public SeniorTicket() {
        super(System.currentTimeMillis() + TICKET_LIFETIME);
    }

    @Override
    public String getTypeId() {
        return "Senior";
    }

    @Override
    public double getPrice() {
        return 2.30;
    }
}
