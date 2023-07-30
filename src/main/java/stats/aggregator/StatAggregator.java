package stats.aggregator;

import stats.entry.StatEntry;
import stats.persistence.StatDataController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * Aggregates data from the given data controller into a single aggregate value.
     * @param data The data controller
     * @param startIndex The start index (inclusive)
     * @param endIndex The end index (inclusive)
     * @return The aggregate value, or empty if no data was found
     */
    public final Optional<A> aggregate(StatDataController data, long startIndex, long endIndex) {
        List<A> aggregates = new ArrayList<>();
        for (long i = startIndex; i <= endIndex; i++) {
            data.getOrAggregate(this, i).ifPresent(aggregates::add);
        }
        if (aggregates.isEmpty()) return Optional.empty();
        return Optional.ofNullable(aggregateExisting(aggregates));
    }

    /**
     * Aggregates a list of entries into a single aggregate value.
     * Precondition: entries is not empty
     */
    public abstract A aggregate(List<E> entries);

    /**
     * Aggregates a list of aggregates into a single aggregate value.
     * Precondition: aggregates is not empty
     */
    public abstract A aggregateExisting(List<A> aggregates);
}
