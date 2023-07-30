package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stats.aggregator.old.CustomerLeaveStationAggregator;
import stats.entry.CustomerLeaveStationEvent;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerLeaveStationAggregatorTest {

    private CustomerLeaveStationAggregator aggregator;

    @BeforeEach
    public void setup() {
        aggregator = new CustomerLeaveStationAggregator();
    }

    @Test
    public void testAggregate() {
        // Assuming CustomerLeaveStationEvent has a constructor that takes a station name
        List<StatEntry> stats = Arrays.asList(
                new CustomerLeaveStationEvent("Station A"),
                new CustomerLeaveStationEvent("Station A"),
                new CustomerLeaveStationEvent("Station B"),
                new CustomerLeaveStationEvent("Station A"),
                new CustomerLeaveStationEvent("Station B"),
                new CustomerLeaveStationEvent("Station C")
        );

        aggregator.aggregate(stats);

        assertEquals(3, aggregator.getLeaves("Station A"));
        assertEquals(2, aggregator.getLeaves("Station B"));
        assertEquals(1, aggregator.getLeaves("Station C"));
        assertEquals(0, aggregator.getLeaves("Station D"));
    }

    @Test
    public void testGetLeavesWithNoEntry() {
        assertEquals(0, aggregator.getLeaves("Station A"));
    }
}
