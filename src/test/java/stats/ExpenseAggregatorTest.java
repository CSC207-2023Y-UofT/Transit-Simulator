package stats;

import com.sun.tools.javac.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseAggregatorTest {

    private ExpenseAggregator aggregator;

    @BeforeEach
    public void setup() {
        aggregator = new ExpenseAggregator();
    }

    @Test
    public void testAggregate() {
        // Assuming ExpenseStat has a constructor that takes a double representing the expense
        List<StatEntry> stats = Arrays.asList(
                new MaintenanceEvent(100.0),
                new MaintenanceEvent(200.0),
                new MaintenanceEvent(300.0),
                new MaintenanceEvent(400.0),
                new MaintenanceEvent(500.0)
        );

        aggregator.aggregate(stats);

        assertEquals(500.0, aggregator.getExpense(), 0.001);
    }

    @Test
    public void testGetExpenseWithNoEntry() {
        assertEquals(0.0, aggregator.getExpense(), 0.001);
    }
}
