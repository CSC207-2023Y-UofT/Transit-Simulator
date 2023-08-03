package stats.persistence;

import stats.entry.StatEntry;

import java.io.IOException;
import java.util.Optional;

/**
 * Interface for storing and retrieving stat aggregates.
 */
public interface StatAggregateDataStore {

    /**
     * Store the stat aggregate {@code aggregate} at the specified
     * time index {@code index} and under the type {@code clazz}.
     *
     * @param index          The time index at which the stat aggregate was recorded.
     * @param entryClass     The type of stat entry that the aggregate was calculated from.
     * @param aggregateClass The type of stat aggregate to store.
     * @param aggregate      The stat aggregate to store.
     * @param <E>            The type of stat entry that the aggregate was calculated from.
     * @param <A>            The type of stat aggregate to store. This should be
     *                       a concrete class, not an interface or abstract class.
     */
    <E extends StatEntry, A> void store(long index,
                                        Class<? extends E> entryClass,
                                        Class<? extends A> aggregateClass,
                                        A aggregate) throws IOException;

    /**
     * Retrieve the stat aggregate that was recorded at the specified
     * time index {@code index} and that is of type {@code clazz}.
     *
     * @param index          The time index at which the stat aggregate was recorded.
     * @param entryClass     The type of stat entry that the aggregate was calculated from.
     * @param aggregateClass The type of stat aggregate to retrieve.
     * @param <E>            The type of stat entry that the aggregate was calculated from.
     * @param <A>            The type of stat aggregate to retrieve. This should be
     *                       a concrete class, not an interface or abstract class.
     * @return The stat aggregate that was recorded at the specified, if any.
     */
    <E extends StatEntry, A> Optional<A> retrieve(long index,
                                                  Class<E> entryClass,
                                                  Class<A> aggregateClass);
}
