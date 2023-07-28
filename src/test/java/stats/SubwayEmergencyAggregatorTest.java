package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stats.aggregator.SubwayEmergencyAggregator;
import stats.event.SubwayEmergencyEvent;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubwayEmergencyAggregatorTest {

    private SubwayEmergencyAggregator aggregator;

    @BeforeEach
    public void setup() {
        aggregator = new SubwayEmergencyAggregator();
    }

    @Test
    public void testAggregate() {
        // Assuming SubwayEmergencyEvent has a constructor that takes an integer representing the line
        List<StatEntry> stats = Arrays.asList(
                new SubwayEmergencyEvent(1),
                new SubwayEmergencyEvent(1),
                new SubwayEmergencyEvent(2),
                new SubwayEmergencyEvent(1),
                new SubwayEmergencyEvent(2),
                new SubwayEmergencyEvent(3)
        );

        aggregator.aggregate(stats);

        assertEquals(6, aggregator.getTotal());
    }

    @Test
    public void testGetTotalWithNoEntry() {
        assertEquals(0, aggregator.getTotal());
    }
}
