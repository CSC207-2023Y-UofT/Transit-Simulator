package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stats.aggregator.old.TrainUsageAggregator;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrainUsageAggregatorTest {

    private TrainUsageAggregator aggregator;

    @BeforeEach
    public void setup() {
        aggregator = new TrainUsageAggregator();
    }

    @Test
    public void testAggregate() {
        // Assuming TrainUsageEvent has a constructor that takes an integer representing the line
        List<StatEntry> stats = Arrays.asList(
                new TrainUsageEvent(1),
                new TrainUsageEvent(1),
                new TrainUsageEvent(2),
                new TrainUsageEvent(1),
                new TrainUsageEvent(2),
                new TrainUsageEvent(3)
        );

        aggregator.aggregate(stats);

        assertEquals(6, TrainUsageAggregator.getTotal());
        assertEquals(3, aggregator.usageCounts.get(1).intValue());
        assertEquals(2, aggregator.usageCounts.get(2).intValue());
        assertEquals(1, aggregator.usageCounts.get(3).intValue());
    }

    @Test
    public void testGetTotalWithNoEntry() {
        assertEquals(0, TrainUsageAggregator.getTotal());
    }
}
