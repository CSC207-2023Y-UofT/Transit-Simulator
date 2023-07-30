package stats.aggregator;

import stats.aggregate.DoubleAggregate;
import stats.entry.StatEntry;

import java.util.List;
import java.util.function.Function;

public abstract class DoubleAggregator<E extends StatEntry, A extends DoubleAggregate>
        extends StatAggregator<E, A> {

    private final Function<E, Double> entryToDouble;
    private final Function<Double, A> aggregateFactory;

    protected DoubleAggregator(
            Class<E> entryClass,
            Class<A> aggregateClass,
            Function<E, Double> entryToDouble,
            Function<Double, A> aggregateFactory
    ) {
        super(entryClass, aggregateClass);
        this.entryToDouble = entryToDouble;
        this.aggregateFactory = aggregateFactory;
    }

    @Override
    public A aggregate(List<E> entries) {
        double total = 0.0;

        for (E entry : entries) {
            total += entryToDouble.apply(entry);
        }

        return aggregateFactory.apply(total);
    }

    @Override
    public A aggregateExisting(List<A> aggregates) {
        double total = 0.0;

        for (A aggregate : aggregates) {
            total += aggregate.getTotal();
        }

        return aggregateFactory.apply(total);
    }
}
