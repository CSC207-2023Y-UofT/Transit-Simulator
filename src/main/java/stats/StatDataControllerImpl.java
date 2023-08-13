package stats;

import persistence.boundary.StatAggregateDataStore;
import persistence.boundary.StatEntryDataStore;
import stats.aggregator.StatAggregator;
import stats.entry.EntryHierarchy;
import stats.entry.StatEntry;
import stats.timing.TimeIndexingStrategy;
import util.Timing;

import java.io.Serializable;
import java.util.*;

/**
 * A controller for the stat data stores. It handles the seamless
 * recording, retrieval, and aggregation of stat entries and aggregates.
 */
public class StatDataControllerImpl implements StatTracker {  // Facade design pattern used!!!


    /**
     * Provides time indices for keeping track of stats.
     */
    private final TimeIndexingStrategy indexStrategy;

    /**
     * DataStore used to persist StatEntry objects.
     */
    private final StatEntryDataStore entryDataStore;

    /**
     * DataStore used to persist aggregate statistics.
     */
    private final StatAggregateDataStore aggregateDataStore;

    /**
     * The current time index. This is used to determine when to aggregate the stats.
     */
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
     * @param indexStrategy  the time index provider, used for calculating time indices.
     * @param entryDataStore     the store for stat entries.
     * @param aggregateDataStore the store for aggregate statistics.
     */
    public StatDataControllerImpl(TimeIndexingStrategy indexStrategy, StatEntryDataStore entryDataStore, StatAggregateDataStore aggregateDataStore) {
        this.indexStrategy = indexStrategy;
        this.entryDataStore = entryDataStore;
        this.aggregateDataStore = aggregateDataStore;

        currTimeIndex = indexStrategy.getTimeIndex();

        EntryHierarchy hierarchy = entryDataStore.retrieveHierarchy();
        hierarchy.getAllLeafClasses().forEach(StatEntry.HIERARCHY::map);
    }

    /**
     * Returns the aggregate data store.
     *
     * @return the aggregate data store.
     */
    public StatAggregateDataStore getAggregateDataStore() {
        return aggregateDataStore;
    }

    // Inherited java docs
    @Override
    public TimeIndexingStrategy getIndexingStrategy() {
        return indexStrategy;
    }

    // Inherited java docs
    @Override
    public void record(StatEntry entry) {

        Class<? extends StatEntry> clazz = entry.getClass(); // Will always be a concrete class
        StatEntry.HIERARCHY.map(clazz);

        synchronized (entries) {
            List<StatEntry> list = entries.getOrDefault(clazz, new ArrayList<>());
            list.add(entry);
            entries.put(clazz, list);
        }
    }

    // Inherited java docs
    @Override
    public void flush() {
        flush(currTimeIndex);
    }

    // Inherited java docs
    @Override
    public synchronized void flush(long index) {
        // Update the current time index
        currTimeIndex = indexStrategy.getTimeIndex();

        // Store the hierarchy first
        entryDataStore.storeHierarchy(StatEntry.HIERARCHY);


        // Store all entries
        for (Map.Entry<Class<? extends StatEntry>, List<StatEntry>> entry : entries.entrySet()) {
            entryDataStore.store(index, entry.getKey(), entry.getValue());
        }

        // Clear the entries
        entries.clear();
    }

    // Inherited java docs
    @Override
    public boolean shouldFlush() {
        return indexStrategy.getTimeIndex() != currTimeIndex;
    }

    // Inherited java docs
    @Override
    public synchronized <E extends StatEntry> List<E> getEntries(Class<E> entryClass, long index) {

        List<Class<? extends E>> concreteClasses = StatEntry.HIERARCHY.getInheritors(entryClass);

        List<E> entries = new ArrayList<>();

        for (Class<? extends E> clazz : concreteClasses) {
            entries.addAll(entryDataStore.retrieve(index, index, clazz)
                    .getOrDefault(index, new ArrayList<>()));
        }

        return entries;
    }

    // Inherited java docs
    @Override
    public synchronized <E extends StatEntry, A> Map<Long, A> getAggregates(Class<E> entryClass, Class<A> aggregateClass, long fromIndex, long toIndexInclusive) {
        return aggregateDataStore.retrieve(fromIndex, toIndexInclusive, entryClass, aggregateClass);
    }

    // Inherited java docs
    @Override
    public synchronized <E extends StatEntry, A extends Serializable> Map<Long, A> getOrAggregate(StatAggregator<E, A> aggregator,
                                                                                                  long startIndex, long endIndexInclusive) {

        // Get the classes that are relevant
        Class<E> entryClass = aggregator.getEntryClass();
        Class<A> aggregateClass = aggregator.getAggregateClass();

        Timing timing = new Timing();
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

                // Get the entries
                Map<Long, ? extends List<? extends E>> retrievedEntries =
                        entryDataStore.retrieve(missingIndices, inheritor);

                timing.mark("retrieveEntries");

                // Merge them into the map
                retrievedEntries.forEach((index, list) -> {
                    entries.putIfAbsent(index, new ArrayList<>());
                    entries.get(index).addAll(list);
                });

            }

            // Aggregate each
            for (long index : missingIndices) {
                List<E> acc = entries.get(index);
                if (acc == null || acc.isEmpty()) continue;
                A aggregate = aggregator.aggregate(acc);
                aggregates.put(index, aggregate);
                // Store the aggregate
                aggregateDataStore.store(index, entryClass, aggregateClass, aggregate);
                timing.mark("aggregate + store");
            }

        }

        return aggregates;
    }

    // Inherited java docs
    @Override
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