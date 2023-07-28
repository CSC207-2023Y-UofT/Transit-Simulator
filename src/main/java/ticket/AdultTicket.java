package ticket;

import model.node.Station;

public class AdultTicket extends Ticket {
    public long validity;
    public static int price;
    public Station departure;
    public Station destination;

    public AdultTicket(Station start, Station end) {
        this.validity = expiration;
        this.departure = start;
        this.destination = end;
    }

    public static void setPrice(int price) {AdultTicket.price = price;
    }

    public static void setExpiration(long time) {AdultTicket.expiration = time;
    }

    @Override
    public String getType() {
        return "Adult";
    }
}
