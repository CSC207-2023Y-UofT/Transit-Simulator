package stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketSalesAggregator implements StatAggregator {


    private int total = 0;
    private Map<String, Integer> ticketCounts = new HashMap<>();


    @Override
    public void aggregate(List<StatEntry> stats) {

        for (StatEntry stat : stats) {

            if (!(stat instanceof TicketSaleStat)) continue;

            TicketSaleStat event = (TicketSaleStat) stat;
            Ticket ticket = event.getTicket();
            String type = ticket.getType();

            total++;

            int curr = ticketCounts.getOrDefault(type, 0);
            ticketCounts.put(type, curr + 1);

        }

    }

    public int getTotal() {
        return total;
    }
}

