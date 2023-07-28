package stats.aggregator.max;

import stats.StatAggregatorRef;
import stats.StatEntry;

import java.util.List;
import java.util.function.Function;

public class MaxAggregator<T extends StatEntry>
        extends StatAggregatorRef<T, MaxAggregate> {

    private final Function<T, Double> supplier;

    public MaxAggregator(Function<T, Double> supplier) {
        this.supplier = supplier;
    }

    @Override
    public MaxAggregate aggregateStats(List<T> entries) {
        checkNotEmpty(entries);
        double max = Double.MIN_VALUE;
        for (T entry : entries) {
            max = Math.max(max, supplier.apply(entry));
        }

        return new MaxAggregate(max);
    }

    @Override
    public MaxAggregate aggregateExisting(List<MaxAggregate> aggregates) {
        checkNotEmpty(aggregates);

        double max = Double.MIN_VALUE;
        for (MaxAggregate aggregate : aggregates) {
            max = Math.max(max, aggregate.value());
        }

        return new MaxAggregate(max);
    }
}
