package stats.aggregator;

import stats.aggregate.SingletonAggregate;
import stats.entry.StatEntry;
import util.Preconditions;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class SingletonAggregator<T, E extends StatEntry, A extends SingletonAggregate<T>>
        extends StatAggregator<E, A> {

    private final Function<E, A> entryConverter;
    private final BiFunction<A, A, A> aggregator;

    protected SingletonAggregator(
            Class<E> entryClass,
            Class<A> aggregateClass,
            Function<E, A> entryConverter,
            BiFunction<A, A, A> aggregator
    ) {
        super(entryClass, aggregateClass);
        this.entryConverter = entryConverter;
        this.aggregator = aggregator;
    }

    @Override
    public A aggregate(List<E> entries) {
        Preconditions.checkArgument(!entries.isEmpty(), "entries is empty");

        A accumulator = entryConverter.apply(entries.get(0));

        for (E entry : entries) {
            accumulator = aggregator.apply(accumulator, entryConverter.apply(entry));
        }

        return accumulator;
    }

    @Override
    public A aggregateExisting(List<A> aggregates) {
        Preconditions.checkArgument(!aggregates.isEmpty(), "aggregates is empty");

        A accumulator = aggregates.get(0);

        for (A aggregate : aggregates) {
            accumulator = aggregator.apply(accumulator, aggregate);
        }

        return accumulator;
    }
}
