package stats.aggregator.min;

import stats.StatAggregatorRef;
import stats.StatEntry;

import java.util.List;
import java.util.function.Function;

public class MinAggregator<T extends StatEntry>
        extends StatAggregatorRef<T, MinAggregate> {

    private final Function<T, Double> supplier;

    public MinAggregator(Function<T, Double> supplier) {
        this.supplier = supplier;
    }

    @Override
    public MinAggregate aggregateStats(List<T> entries) {
        checkNotEmpty(entries);
        double max = Double.MIN_VALUE;
        for (T entry : entries) {
            max = Math.max(max, supplier.apply(entry));
        }

        return new MinAggregate(max);
    }

    @Override
    public MinAggregate aggregateExisting(List<MinAggregate> aggregates) {
        checkNotEmpty(aggregates);

        double max = Double.MIN_VALUE;
        for (MinAggregate aggregate : aggregates) {
            max = Math.max(max, aggregate.value());
        }

        return new MinAggregate(max);
    }
}
