package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stats.aggregator.old.ProfitAggregator;
import stats.entry.MaintenanceEvent;
import stats.entry.TicketSaleStat;
import ticket.AdultTicket;
import ticket.ChildTicket;
import ticket.SeniorTicket;
import ticket.StudentTicket;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfitAggregatorTest {

    private ProfitAggregator aggregator;

    @BeforeEach
    public void setup() {
        aggregator = new ProfitAggregator();
    }

    @Test
    public void testAggregate() {
        List<StatEntry> stats = Arrays.asList(
                new TicketSaleStat(new ChildTicket()),          // 1
                new TicketSaleStat(new AdultTicket()),
                new TicketSaleStat(new SeniorTicket()),
                new TicketSaleStat(new StudentTicket()),

                new TicketSaleStat(new ChildTicket()),          // 2
                new TicketSaleStat(new AdultTicket()),
                new TicketSaleStat(new SeniorTicket()),
                new TicketSaleStat(new StudentTicket()),

                new TicketSaleStat(new ChildTicket()),         // 3
                new TicketSaleStat(new AdultTicket()),
                new TicketSaleStat(new SeniorTicket()),
                new TicketSaleStat(new StudentTicket()),

                new TicketSaleStat(new ChildTicket()),        // 4
                new TicketSaleStat(new AdultTicket()),
                new TicketSaleStat(new SeniorTicket()),
                new TicketSaleStat(new StudentTicket()),

                new TicketSaleStat(new ChildTicket()),      // 5
                new TicketSaleStat(new AdultTicket()),
                new TicketSaleStat(new SeniorTicket()),
                new TicketSaleStat(new StudentTicket()),

                new TicketSaleStat(new ChildTicket()),    // 6
                new TicketSaleStat(new AdultTicket()),
                new TicketSaleStat(new SeniorTicket()),
                new TicketSaleStat(new StudentTicket()),

                new MaintenanceEvent(20.0),
                new MaintenanceEvent(10.0)
        );

        aggregator.aggregate(stats);

        // Total revenue is 6(2.40 + 3.35 + 2.30 + 2.35) = 62.4, total expense is 20.0 + 10.0 = 30.0
        // So, profit should be 62.4 - 30.0 = 32.4
        assertEquals(1200.0, aggregator.getProfit(), 0.001);
    }

    @Test
    public void testGetProfitWithNoEntry() {
        assertEquals(0.0, aggregator.getProfit(), 0.001);
    }
}
