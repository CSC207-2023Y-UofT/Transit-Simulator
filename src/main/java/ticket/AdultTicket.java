package ticket;

import model.node.Station;


// TODO: are we using the expiry date?

public class AdultTicket extends Ticket {

    public static final long TICKET_LIFETIME = 1000 * 60 * 60 * 24L; // 1 day

    public AdultTicket() {
        super(System.currentTimeMillis() + TICKET_LIFETIME);
    }

    @Override
    public double getPrice() {
        return 3.35;
    }

    @Override
    public String getTypeId() {
        return "Adult";
    }
}
