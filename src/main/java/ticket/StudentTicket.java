package ticket;

import model.node.Station;

import java.util.Objects;
import java.util.Scanner;

class StudentTicket extends Ticket{

    public static final long TICKET_LIFETIME = 1000 * 60 * 60 * 24L; // 1 day

    public StudentTicket() {
        super(System.currentTimeMillis() + TICKET_LIFETIME);
    }

    @Override
    public double getPrice() {
        return 2.35;
    }

    @Override
    public String getType(){
        return "Student";
    }
}
