package stats;

import app_business.boundary.IStatInteractor;
import persistence.boundary.StatAggregateDataStore;
import persistence.boundary.StatEntryDataStore;
import stats.aggregator.StatAggregator;
import stats.entry.EntryHierarchy;
import stats.entry.StatEntry;
import stats.timing.TimeIndexProvider;
import util.Timing;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * A controller for the stat data stores. It handles the seamless
 * recording, retrieval, and aggregation of stat entries and aggregates.
 */
public class StatDataController {  // Facade design pattern used!!!


    private final TimeIndexProvider timeIndexProvider;

    /**
     * DataStore used to persist StatEntry objects.
     */
    private final StatEntryDataStore entryDataStore;

    /**
     * DataStore used to persist aggregate statistics.
     */
    private final StatAggregateDataStore aggregateDataStore;

    private long currTimeIndex;

    /**
     * A map of stat entry classes to the stat entries that have been recorded
     * during this time interval. They are stored in lists mapped by
     * their class.
     */
    private final Map<Class<? extends StatEntry>, List<StatEntry>> entries = new HashMap<>();


    /**
     * Constructs a StatDataController instance with a given EntryDataStore and AggregateDataStore.
     *
     * @param timeIndexProvider  the time index provider, used for calculating time indices.
     * @param entryDataStore     the store for stat entries.
     * @param aggregateDataStore the store for aggregate statistics.
     */
    public StatDataController(TimeIndexProvider timeIndexProvider, StatEntryDataStore entryDataStore, StatAggregateDataStore aggregateDataStore) {
        this.timeIndexProvider = timeIndexProvider;
        this.entryDataStore = entryDataStore;
        this.aggregateDataStore = aggregateDataStore;

        currTimeIndex = timeIndexProvider.getTimeIndex();

        EntryHierarchy hierarchy = entryDataStore.retrieveHierarchy();
        hierarchy.getAllLeafClasses().forEach(StatEntry.HIERARCHY::map);
    }

    /**
     * Returns the entry data store.
     *
     * @return the entry data store.
     */
    public StatEntryDataStore getEntryDataStore() {
        return entryDataStore;
    }

    /**
     * Returns the aggregate data store.
     *
     * @return the aggregate data store.
     */
    public StatAggregateDataStore getAggregateDataStore() {
        return aggregateDataStore;
    }

    /**
     * Returns the time index provider.
     */
    public TimeIndexProvider getTimeIndexProvider() {
        return timeIndexProvider;
    }

    /**
     * Record a stat entry.
     *
     * @param entry The stat entry to record.
     */
    public void record(StatEntry entry) {

        Class<? extends StatEntry> clazz = entry.getClass(); // Will always be a concrete class
        StatEntry.HIERARCHY.map(clazz);

        synchronized (entries) {
            List<StatEntry> list = entries.getOrDefault(clazz, new ArrayList<>());
            list.add(entry);
            entries.put(clazz, list);
        }
    }

    public void flush() {
        flush(currTimeIndex);
    }

    /**
     * Flush all recorded stat entries to the data store.
     */
    public synchronized void flush(long index) {
        // Update the current time index
        currTimeIndex = timeIndexProvider.getTimeIndex();

        // Store the hierarchy first
        entryDataStore.storeHierarchy(StatEntry.HIERARCHY);


        // Store all entries
        for (Map.Entry<Class<? extends StatEntry>, List<StatEntry>> entry : entries.entrySet()) {
            try {
                entryDataStore.store(index, entry.getKey(), entry.getValue());  // TODO: unhandled IOException
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Clear the entries
        entries.clear();
    }

    public boolean shouldFlush() {
        return timeIndexProvider.getTimeIndex() != currTimeIndex;
    }

    /**
     * Retrieve all stat entries of type {@code entryClass} that were recorded
     * at the specified time index {@code index}.
     *
     * @param entryClass The type of stat entries to retrieve.
     * @param index      The time index at which the stat entries were recorded.
     * @param <E>        The type of stat entries to retrieve.
     * @return A list of stat entries of type {@code entryClass} that were
     */
    public synchronized <E extends StatEntry> List<E> getEntries(Class<E> entryClass, long index) {

        List<Class<? extends E>> concreteClasses = StatEntry.HIERARCHY.getInheritors(entryClass);

        List<E> entries = new ArrayList<>();

        for (Class<? extends E> clazz : concreteClasses) {
            try {
                entries.addAll(entryDataStore.retrieve(index, index, clazz)
                        .getOrDefault(index, new ArrayList<>()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return entries;
    }

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
    public synchronized <E extends StatEntry, A> Map<Long, A> getAggregates(Class<E> entryClass, Class<A> aggregateClass, long fromIndex, long toIndexInclusive) {
        try {
            return aggregateDataStore.retrieve(fromIndex, toIndexInclusive, entryClass, aggregateClass);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

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
    public synchronized <E extends StatEntry, A extends Serializable> Map<Long, A> getOrAggregate(StatAggregator<E, A> aggregator,
                                                                                                  long startIndex, long endIndexInclusive) {

        // Get the classes that are relevant
        Class<E> entryClass = aggregator.getEntryClass();
        Class<A> aggregateClass = aggregator.getAggregateClass();

        Timing timing = new Timing("getOrAggregate");
        timing.start();

        // Get all of those aggregates
        Map<Long, A> aggregates = getAggregates(entryClass, aggregateClass, startIndex, endIndexInclusive);

        timing.mark("getAggregates");

        // Accumulate all the indices that don't exist
        List<Long> missingIndices = new ArrayList<>();
        for (long index = startIndex; index <= endIndexInclusive; index++) {
            if (aggregates.containsKey(index)) continue;
            missingIndices.add(index);
        }

        timing.mark("missingIndices");

        // If there are indeed missing indices, aggregate them if
        // possible and store them
        if (!missingIndices.isEmpty()) {

            // Map for the entry lists
            Map<Long, List<E>> entries = new HashMap<>();

            // For each inheritor class, retrieve the entries of that class
            for (Class<? extends E> inheritor : StatEntry.HIERARCHY.getInheritors(entryClass)) {
                try {

                    // Get the entries
                    Map<Long, ? extends List<? extends E>> retrievedEntries =
                            entryDataStore.retrieve(missingIndices, inheritor);

                    timing.mark("retrieveEntries");

                    // Merge them into the map
                    retrievedEntries.forEach((index, list) -> {
                        entries.putIfAbsent(index, new ArrayList<>());
                        entries.get(index).addAll(list);
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Aggregate each
            for (long index : missingIndices) {
                List<E> acc = entries.get(index);
                if (acc == null || acc.isEmpty()) continue;
                A aggregate = aggregator.aggregate(acc);
                aggregates.put(index, aggregate);
                // Store the aggregate
                try {
                    aggregateDataStore.store(index, entryClass, aggregateClass, aggregate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                timing.mark("aggregate + store");
            }

        }

        return aggregates;
    }

    public <E extends StatEntry, A extends Serializable> Optional<A> aggregateCurrent(
            StatAggregator<E, A> aggregator
    ) {
        Class<E> entryClass = aggregator.getEntryClass();

        List<E> acc = new ArrayList<>();

        List<Class<? extends E>> inheritors = StatEntry.HIERARCHY.getInheritors(entryClass);
        for (Class<? extends E> inheritor : inheritors) {
            synchronized (entries) {
                entries.getOrDefault(inheritor, new ArrayList<>())
                        .forEach(e -> acc.add(entryClass.cast(e)));
            }
        }

        if (acc.isEmpty()) return Optional.empty();

        return Optional.ofNullable(aggregator.aggregate(acc));
    }

}
