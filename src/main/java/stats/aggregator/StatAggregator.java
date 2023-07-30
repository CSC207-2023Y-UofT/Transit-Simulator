package stats.aggregator;

import stats.entry.StatEntry;
import stats.persistence.StatDataController;

import java.util.ArrayList;
import java.util.List;

/**
 * Aggregates data from a {@link StatDataController} into a single aggregate value.
 * @param <E> The entry class
 * @param <A> The aggregator class
 */
public abstract class StatAggregator<E extends StatEntry, A> {

    public final A aggregate(StatDataController data, long startIndex, long endIndex) {
        List<A> aggregates = new ArrayList<>();
        for (long i = startIndex; i <= endIndex; i++) {
            aggregates.add(data.getOrAggregate(this, i));
        }
        return aggregateExisting(aggregates);
    }

    public abstract Class<E> getEntryClass();
    public abstract Class<A> getAggregateClass();

    public abstract A aggregate(List<E> entries);
    public abstract A aggregateExisting(List<A> aggregates);
}
