package ticket;

import model.node.Station;

public class ChildTicket extends Ticket {
    private static int MAX_AGE = 15;
    public static long TICKET_LIFETIME = 1000 * 60 * 60 * 24L; // 1 day


    public ChildTicket() {
        super(System.currentTimeMillis() + TICKET_LIFETIME);
    }

    @Override
    public String getType() {
        return "Child";
    }

    @Override
    public double getPrice() {
        return 3.99;
    }
}

