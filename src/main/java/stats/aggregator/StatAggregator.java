package stats.aggregator;

import stats.entry.StatEntry;
import stats.persistence.StatDataController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregates data from a {@link StatDataController} into a single aggregate value.
 *
 * @param <E> The entry class
 * @param <A> The aggregator class
 */
public abstract class StatAggregator<E extends StatEntry, A extends Serializable> {

    private final Class<E> entryClass;
    private final Class<A> aggregateClass;

    protected StatAggregator(Class<E> entryClass, Class<A> aggregateClass) {
        this.entryClass = entryClass;
        this.aggregateClass = aggregateClass;
    }

    public final Class<E> getEntryClass() {
        return entryClass;
    }

    public final Class<A> getAggregateClass() {
        return aggregateClass;
    }

    public final A aggregate(StatDataController data, long startIndex, long endIndex) {
        List<A> aggregates = new ArrayList<>();
        for (long i = startIndex; i <= endIndex; i++) {
            aggregates.add(data.getOrAggregate(this, i));
        }
        return aggregateExisting(aggregates);
    }

    public abstract A aggregate(List<E> entries);

    public abstract A aggregateExisting(List<A> aggregates);
}
