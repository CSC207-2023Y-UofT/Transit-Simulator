package stats.persistence;

import stats.aggregator.StatAggregator;
import stats.entry.EntryHierarchy;
import stats.entry.StatEntry;

import java.io.Serializable;
import java.util.*;

/**
 * A controller for the stat data stores. It handles the seamless
 * recording, retrieval, and aggregation of stat entries and aggregates.
 */
public class StatDataController {

    /**
     * DataStore used to persist StatEntry objects.
     */
    private final StatEntryDataStore entryDataStore;

    /**
     * DataStore used to persist aggregate statistics.
     */
    private final StatAggregateDataStore aggregateDataStore;

    /**
     * A map of stat entry classes to the stat entries that have been recorded
     * during this time interval. They are stored in lists mapped by
     * their class.
     */
    private final Map<Class<? extends StatEntry>, List<StatEntry>> entries = new HashMap<>();


    /**
     * Constructs a StatDataController instance with a given EntryDataStore and AggregateDataStore.
     *
     * @param entryDataStore the store for stat entries.
     * @param aggregateDataStore the store for aggregate statistics.
     */
    public StatDataController(StatEntryDataStore entryDataStore, StatAggregateDataStore aggregateDataStore) {
        this.entryDataStore = entryDataStore;
        this.aggregateDataStore = aggregateDataStore;

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
     * Record a stat entry.
     *
     * @param entry The stat entry to record.
     */
    public void record(StatEntry entry) {

        Class<? extends StatEntry> clazz = entry.getClass(); // Will always be a concrete class
        StatEntry.HIERARCHY.map(clazz);

        List<StatEntry> list = entries.getOrDefault(clazz, new ArrayList<>());
        list.add(entry);
        entries.put(clazz, list);
    }

    /**
     * Flush all recorded stat entries to the data store.
     *
     * @param index The time index at which the stat entries should be considered
     *              to have been recorded.
     */
    public void flush(long index) {

        // Store the hierarchy first
        entryDataStore.storeHierarchy(StatEntry.HIERARCHY);

        // Store all entries
        for (Map.Entry<Class<? extends StatEntry>, List<StatEntry>> entry : entries.entrySet()) {
            entryDataStore.store(index, entry.getKey(), entry.getValue());  // TODO: unhandled IOException
        }

        // Clear the entries
        entries.clear();
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
    public <E extends StatEntry> List<E> getEntries(Class<E> entryClass, long index) {

        List<Class<? extends E>> concreteClasses = StatEntry.HIERARCHY.getInheritors(entryClass);

        List<E> entries = new ArrayList<>();

        for (Class<? extends E> clazz : concreteClasses) {
            entries.addAll(entryDataStore.retrieve(index, clazz));
        }

        return entries;
    }

    /**
     * Retrieve the aggregate of type {@code aggregateClass} that was
     * recorded at the specified time index {@code index}.
     *
     * @param entryClass     The type of stat entries that were aggregated.
     * @param aggregateClass The type of aggregate to retrieve.
     * @param index          The time index at which the aggregate was recorded.
     * @param <E>            The type of stat entries that were aggregated.
     * @param <A>            The type of aggregate to retrieve.
     * @return The aggregate of type {@code aggregateClass} that was
     * recorded at the specified time index {@code index}.
     */
    public <E extends StatEntry, A> Optional<A> getAggregate(Class<E> entryClass, Class<A> aggregateClass, long index) {
        return aggregateDataStore.retrieve(index, entryClass, aggregateClass);
    }

    /**
     * Aggregate the data of type {@code entryClass} that was recorded
     * at the specified time index {@code index} using the specified
     * {@code aggregator}. This will search for existing aggregations first,
     * and if none are found, it will aggregate the data and store the
     * resulting aggregate.
     *
     * @param aggregator The aggregator to use to aggregate the data.
     * @param index      The time index at which the data was recorded.
     * @param <E>        The type of stat entries to aggregate.
     * @param <A>        The type of aggregate to retrieve.
     */
    public <E extends StatEntry, A extends Serializable> Optional<A> getOrAggregate(StatAggregator<E, A> aggregator, long index) {

        // Get the classes that are relevant
        Class<E> entryClass = aggregator.getEntryClass();
        Class<A> aggregateClass = aggregator.getAggregateClass();

        // Check if the aggregate already exists
        A aggregate = getAggregate(entryClass, aggregateClass, index).orElse(null);

        // If it doesn't, aggregate the data and store the result
        if (aggregate == null) {

            // Get the data to aggregate
            List<E> entries = getEntries(entryClass, index);
            if (entries.isEmpty()) return Optional.empty(); // No data

            // Aggregate the data
            aggregate = aggregator.aggregate(entries);

            // Store the aggregate
            aggregateDataStore.store(index, entryClass, aggregateClass, aggregate);
        }

        return Optional.ofNullable(aggregate);
    }

}
