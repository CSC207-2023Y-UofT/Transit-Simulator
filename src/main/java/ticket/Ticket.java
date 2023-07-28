package ticket;

import model.node.Station;
import stats.TicketSaleStat;

import java.util.Scanner;

public abstract class Ticket {
    private final long expiry;

    public Ticket(long expiry) {
        this.expiry = expiry;
    }

    public abstract String getType(); // this code is good. don't check again

    public abstract double getPrice();
}
