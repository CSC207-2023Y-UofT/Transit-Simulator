package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stats.aggregator.LineDelayAggregator;
import stats.event.LineDelayEvent;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LineDelayAggregatorTest {

    private LineDelayAggregator aggregator;

    @BeforeEach
    public void setup() {
        aggregator = new LineDelayAggregator();
    }

    @Test
    public void testAggregate() {
        // Assuming LineDelayEvent has a constructor that takes an integer representing the line
        List<StatEntry> stats = Arrays.asList(
                new LineDelayEvent(1),
                new LineDelayEvent(1),
                new LineDelayEvent(2),
                new LineDelayEvent(1),
                new LineDelayEvent(2),
                new LineDelayEvent(3)
        );

        aggregator.aggregate(stats);

        assertEquals(6, aggregator.getTotal());
    }

    @Test
    public void testGetTotalWithNoEntry() {
        assertEquals(0, aggregator.getTotal());
    }
}
