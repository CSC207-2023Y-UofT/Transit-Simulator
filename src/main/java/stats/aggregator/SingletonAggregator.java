package stats.aggregator;

import stats.aggregate.SingletonAggregate;
import stats.entry.StatEntry;

import java.util.List;
import java.util.function.Function;

public abstract class SingletonAggregator<T, E extends StatEntry, A extends SingletonAggregate<T>>
        extends StatAggregator<E, A> {

    private final Function<E, Double> entryToDouble;
    private final Function<Double, A> aggregateFactory;
    private final Function<T, T> aggregator;

    protected SingletonAggregator(
            Class<E> entryClass,
            Class<A> aggregateClass,
            Function<E, Double> entryToDouble,
            Function<Double, A> aggregateFactory,
            Function<T, T> aggregator
    ) {
        super(entryClass, aggregateClass);
        this.entryToDouble = entryToDouble;
        this.aggregateFactory = aggregateFactory;
        this.aggregator = aggregator;
    }

    @Override
    public A aggregate(List<E> entries) {
        T total = null;

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
