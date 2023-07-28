package stats.aggregator;

import stats.StatAggregator;
import stats.StatEntry;
import stats.event.TicketSaleStat;
import ticket.Ticket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Aggregator for the total number of ticket sales.
 */
public class TicketSalesAggregator implements StatAggregator {

    /**
     * The total number of ticket sales.
     */
    private int total = 0;

    /**
     * The number of ticket sales per type.
     */
    public Map<String, Integer> ticketCounts = new HashMap<>();

    /**
     * Aggregate the total number of ticket sales.
     */
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

    /**
     * Return the number of ticket sales per type.
     */
    public int getTotal() {
        return total;
    }
}

