package ticket;

import model.node.Station;

public interface TicketInterface {
    void ticketSelling(Station dep, Station des);
    String getType();
}