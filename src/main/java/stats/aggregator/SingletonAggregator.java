package stats.aggregator;

import stats.aggregate.SingletonAggregate;
import stats.entry.StatEntry;
import util.Preconditions;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Abstract class for aggregating statistics.
 * SingletonAggregator extends StatAggregator to aggregate StatEntry entries into a SingletonAggregate.
 */
public abstract class SingletonAggregator<E extends StatEntry, A extends SingletonAggregate<?>>
        extends StatAggregator<E, A> {

    /**
     * The function that converts a StatEntry into a SingletonAggregate.
     */
    private final Function<E, A> entryConverter;

    /**
     * The function that combines two SingletonAggregates into one.
     */
    private final BiFunction<A, A, A> aggregator;

    /**
     * Constructor for the SingletonAggregator class.
     *
     * @param entryClass The class of entries that will be aggregated.
     * @param aggregateClass The class of SingletonAggregate that the entries will be aggregated into.
     * @param entryConverter A function that converts a StatEntry into a SingletonAggregate.
     * @param aggregator A function that combines two SingletonAggregates into one.
     */
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

    /**
     * Aggregates a list of entries into a SingletonAggregate.
     *
     * @param entries List of StatEntry entries to be aggregated.
     * @return A SingletonAggregate representing the aggregation of the entries.
     */
    @Override
    public A aggregate(List<E> entries) {
        Preconditions.checkArgument(!entries.isEmpty(), "entries is empty");

        A accumulator = entryConverter.apply(entries.get(0));

        for (int i = 1; i < entries.size(); i++) {
            accumulator = aggregator.apply(accumulator, entryConverter.apply(entries.get(i)));
        }

        return accumulator;
    }

    /**
     * Aggregates a list of existing SingletonAggregate objects into a new SingletonAggregate.
     *
     * @param aggregates List of existing SingletonAggregate objects to be aggregated.
     * @return A new SingletonAggregate representing the aggregation of the existing aggregates.
     */
    @Override
    public A aggregateExisting(List<A> aggregates) {
        Preconditions.checkArgument(!aggregates.isEmpty(), "aggregates is empty");

        A accumulator = aggregates.get(0);

        for (int i = 1; i < aggregates.size(); i++) {
            accumulator = aggregator.apply(accumulator, aggregates.get(i));
        }

        return accumulator;
    }

}
