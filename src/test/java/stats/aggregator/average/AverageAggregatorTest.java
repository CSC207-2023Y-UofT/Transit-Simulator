package stats.aggregator.average;

import org.junit.jupiter.api.Test;
import stats.StatEntry;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AverageAggregatorTest {

    public static class ExampleStat implements StatEntry {
        private final double value;

        public ExampleStat(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

    List<ExampleStat> stats1 = List.of(
            new ExampleStat(1),
            new ExampleStat(2),
            new ExampleStat(3),
            new ExampleStat(4),
            new ExampleStat(5)
    );

    List<ExampleStat> stats2 = List.of(
            new ExampleStat(2),
            new ExampleStat(3),
            new ExampleStat(4),
            new ExampleStat(5)
    );

    @Test
    public void testAggregateStats() {

        AverageAggregator<ExampleStat> aggregator = new AverageAggregator<>(ExampleStat::getValue);
        AverageAggregate aggregate = aggregator.aggregateStats(stats1);

        assertEquals(3, aggregate.value());
        assertEquals(5, aggregate.getCount());
    }

    @Test
    public void testAggregateExisting() {
        AverageAggregator<ExampleStat> aggregator = new AverageAggregator<>(ExampleStat::getValue);
        AverageAggregate aggregate = aggregator.aggregateStats(stats1);
        AverageAggregate aggregate2 = aggregator.aggregateStats(stats2);

        AverageAggregate finalAggregate = aggregator.aggregateExisting(
                List.of(aggregate, aggregate2)
        );

        assertEquals(29 / 9.0, finalAggregate.value());
    }

}