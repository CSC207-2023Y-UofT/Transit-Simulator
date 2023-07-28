package ticket;

import model.node.Station;

public class AdultTicket extends Ticket {

    public static final long TICKET_LIFETIME = 1000 * 60 * 60 * 24L; // 1 day

    public AdultTicket() {
        super(System.currentTimeMillis() + TICKET_LIFETIME);
    }

    @Override
    public double getPrice() {
        return 5.0;
    }

    @Override
    public String getType() {
        return "Adult";
    }
}
