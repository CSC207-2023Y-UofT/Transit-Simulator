package stats;

import stats.aggregator.StatAggregator;
import stats.entry.StatEntry;
import stats.timing.TimeIndexingStrategy;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StatTracker {
    /**
     * Record a stat entry.
     *
     * @param entry The stat entry to record.
     */
    void record(StatEntry entry);

    /**
     * Returns the time indexing strategy
     */
    TimeIndexingStrategy getIndexingStrategy();

    /**
     * Flush all recorded stat entries to the data store.
     */
    void flush();

    /**
     * Flush all recorded stat entries to the data store at a specific
     * index.
     */
    void flush(long index);

    /**
     * Returns whether this stat tracker should be flushed
     * @return true if this stat tracker should be flushed, false otherwise.
     */
    boolean shouldFlush();

    /**
     * Retrieve all stat entries of type {@code entryClass} that were recorded
     * at the specified time index {@code index}.
     *
     * @param entryClass The type of stat entries to retrieve.
     * @param index      The time index at which the stat entries were recorded.
     * @param <E>        The type of stat entries to retrieve.
     * @return A list of stat entries of type {@code entryClass} that were
     */
    <E extends StatEntry> List<E> getEntries(Class<E> entryClass, long index);

    /**
     * Retrieve the aggregate of type {@code aggregateClass} that was
     * recorded at the specified time index {@code index}.
     *
     * @param entryClass       The type of stat entries that were aggregated.
     * @param aggregateClass   The type of aggregate to retrieve.
     * @param fromIndex        The beginning  time index at which the stat entries were recorded.
     * @param toIndexInclusive The ending time index at which the stat entries were recorded.
     * @param <E>              The type of stat entries that were aggregated.
     * @param <A>              The type of aggregate to retrieve.
     * @return The aggregate of type {@code aggregateClass} that was
     * recorded at the specified time index {@code index}.
     */
    <E extends StatEntry, A> Map<Long, A> getAggregates(Class<E> entryClass, Class<A> aggregateClass, long fromIndex, long toIndexInclusive);

    /**
     * Aggregate the data of type {@code entryClass} that was recorded
     * at the specified time index {@code index} using the specified
     * {@code aggregator}. This will search for existing aggregations first,
     * and if none are found, it will aggregate the data and store the
     * resulting aggregate.
     *
     * @param aggregator        The aggregator to use to aggregate the data.
     * @param startIndex        The beginning  time index at which the stat entries were recorded.
     * @param endIndexInclusive The ending time index at which the stat entries were recorded.
     * @param <E>               The type of stat entries to aggregate.
     * @param <A>               The type of aggregate to retrieve.
     */
    <E extends StatEntry, A extends Serializable> Map<Long, A> getOrAggregate(StatAggregator<E, A> aggregator,
                                                                              long startIndex, long endIndexInclusive);

    /**
     * Aggregates the current statistics based on a provided aggregator.
     *
     * <p>This method fetches all instances of the specified stat entry type and its inheritors
     * from a predefined entry storage and then aggregates these entries using the given
     * aggregator. If there are no entries to aggregate, the method will return an empty optional.</p>
     *
     * @param <E>        Type of the stat entry, which must extend {@code StatEntry}.
     * @param <A>        Type of the aggregation result, which must be serializable.
     * @param aggregator The aggregator responsible for processing the entries.
     * @return An optional containing the aggregated result if there are entries,
     * or an empty optional otherwise.
     * @throws ClassCastException if any of the entries can't be cast to the {@code entryClass}.
     */
    <E extends StatEntry, A extends Serializable> Optional<A> aggregateCurrent(
            StatAggregator<E, A> aggregator
    );
}
