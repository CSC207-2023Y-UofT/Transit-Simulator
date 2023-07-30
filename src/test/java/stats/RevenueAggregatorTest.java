package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stats.aggregator.old.RevenueAggregator;
import stats.entry.TicketSaleStat;
import ticket.SeniorTicket;
import ticket.StudentTicket;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RevenueAggregatorTest {

    private RevenueAggregator aggregator;

    @BeforeEach
    public void setup() {
        aggregator = new RevenueAggregator();
    }

    @Test
    public void testAggregate() {
        // Assuming RevenueStat has a constructor that takes a double for the revenue
        List<StatEntry> stats = Arrays.asList(
                new TicketSaleStat(new SeniorTicket()),
                new TicketSaleStat(new SeniorTicket()),
                new TicketSaleStat(new StudentTicket()),
                new TicketSaleStat(new StudentTicket()),
                new TicketSaleStat(new StudentTicket()),
                new TicketSaleStat(new StudentTicket())
        );

        aggregator.aggregate(stats);

        // The total of the revenues should be 2.3 * 2 + 2.35 * 4 = 14.8
        assertEquals(14.8, aggregator.getRevenue(), 0.001);
    }

    @Test
    public void testGetRevenueWithNoEntry() {
        assertEquals(0.0, aggregator.getRevenue(), 0.001);
    }
}
