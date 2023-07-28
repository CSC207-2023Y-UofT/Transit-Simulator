package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import stats.aggregator.TicketSalesAggregator;
import stats.event.TicketSaleStat;
import ticket.AdultTicket;
import ticket.ChildTicket;
import ticket.SeniorTicket;

public class TicketSalesAggregatorTest {

    private TicketSalesAggregator aggregator;

    @BeforeEach
    public void setup() {
        aggregator = new TicketSalesAggregator();
    }

    @Test
    public void testAggregate() {
        // Assuming TicketSaleStat has a constructor that takes a Ticket
        // and Ticket has a constructor that takes a String representing the type
        List<StatEntry> stats = Arrays.asList(
                new TicketSaleStat(new AdultTicket()),
                new TicketSaleStat(new AdultTicket()),
                new TicketSaleStat(new ChildTicket()),
                new TicketSaleStat(new AdultTicket()),
                new TicketSaleStat(new SeniorTicket()),
                new TicketSaleStat(new ChildTicket())
        );

        aggregator.aggregate(stats);

        assertEquals(6, aggregator.getTotal());
        assertEquals(3, aggregator.ticketCounts.get("Adult").intValue());
        assertEquals(2, aggregator.ticketCounts.get("Child").intValue());
        assertEquals(1, aggregator.ticketCounts.get("Senior").intValue());
    }

    @Test
    public void testGetTotalWithNoEntry() {
        assertEquals(0, aggregator.getTotal());
    }
}
