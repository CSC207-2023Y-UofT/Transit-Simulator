package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerEnterStationAggregatorTest {

    private CustomerEnterStationAggregator aggregator;

    @BeforeEach
    public void setup() {
        aggregator = new CustomerEnterStationAggregator();
    }

    @Test
    public void testAggregate() {
        // Assuming CustomerEnterStationEvent has a constructor that takes a station name
        List<StatEntry> stats = Arrays.asList(
                new CustomerEnterStationEvent("Station A"),
                new CustomerEnterStationEvent("Station A"),
                new CustomerEnterStationEvent("Station B"),
                new CustomerEnterStationEvent("Station A"),
                new CustomerEnterStationEvent("Station B"),
                new CustomerEnterStationEvent("Station C")
        );

        aggregator.aggregate(stats);

        assertEquals(3, aggregator.getEntries("Station A"));
        assertEquals(2, aggregator.getEntries("Station B"));
        assertEquals(1, aggregator.getEntries("Station C"));
        assertEquals(0, aggregator.getEntries("Station D"));
    }

    @Test
    public void testGetEntriesWithNoEntry() {
        assertEquals(0, aggregator.getEntries("Station A"));
    }
}
