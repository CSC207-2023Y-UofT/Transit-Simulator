package stats.aggregator;

import stats.entry.StatEntry;
import stats.persistence.StatDataController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An abstract class that aggregates data from a {@link StatDataController} into a single aggregate value.
 *
 * @param <E> The type of entries this aggregator operates on, which must extend {@link StatEntry}.
 * @param <A> The type of aggregated data, which must be serializable.
 */
public abstract class StatAggregator<E extends StatEntry, A extends Serializable> {

    /**
     * The class of entries this aggregator operates on.
     */
    private final Class<E> entryClass;

    /**
     * The class of the aggregated data.
     */
    private final Class<A> aggregateClass;

    /**
     * Constructs a StatAggregator that operates on the given entry and aggregate classes.
     *
     * @param entryClass     The class of entries this aggregator operates on.
     * @param aggregateClass The class of aggregated data.
     */
    protected StatAggregator(Class<E> entryClass, Class<A> aggregateClass) {
        this.entryClass = entryClass;
        this.aggregateClass = aggregateClass;
    }

    /**
     * Returns the entry class this aggregator operates on.
     *
     * @return The entry class.
     */
    public final Class<E> getEntryClass() {
        return entryClass;
    }

    /**
     * Returns the class of the aggregated data.
     *
     * @return The aggregate class.
     */
    public final Class<A> getAggregateClass() {
        return aggregateClass;
    }

    /**
     * Aggregates data from the given data controller into a single aggregate value.
     *
     * @param data       The data controller.
     * @param startIndex The start index (inclusive) of the range of entries to aggregate.
     * @param endIndex   The end index (inclusive) of the range of entries to aggregate.
     * @return An Optional containing the aggregate value, or an empty Optional if no data was found in the given range.
     */
    public final Optional<A> aggregate(StatDataController data, long startIndex, long endIndex) {
        List<A> aggregates = new ArrayList<>(data.getOrAggregate(this, startIndex, endIndex).values());
        if (aggregates.isEmpty()) return Optional.empty();
        return Optional.ofNullable(aggregateExisting(aggregates));
    }

    /**
     * Aggregates a list of entries into a single aggregate value.
     *
     * @param entries The list of entries to aggregate.
     * @return The aggregated value.
     * @throws IllegalArgumentException if entries list is empty.
     */
    public abstract A aggregate(List<E> entries);

    /**
     * Aggregates a list of existing aggregates into a single aggregate value.
     *
     * @param aggregates The list of existing aggregates to aggregate.
     * @return The aggregated value.
     * @throws IllegalArgumentException if aggregates list is empty.
     */
    public abstract A aggregateExisting(List<A> aggregates);

}
