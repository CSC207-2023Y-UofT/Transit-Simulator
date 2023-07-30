package stats.persistence;

import stats.aggregator.StatAggregator;
import stats.entry.EntryHierarchy;
import stats.entry.StatEntry;

import java.util.*;

public class StatDataController {
    private final StatEntryDataStore entryDataStore;
    private final StatAggregateDataStore aggregateDataStore;

    private final Map<Class<? extends StatEntry>, List<StatEntry>> entries = new HashMap<>();

    public StatDataController(StatEntryDataStore entryDataStore, StatAggregateDataStore aggregateDataStore) {
        this.entryDataStore = entryDataStore;
        this.aggregateDataStore = aggregateDataStore;

        EntryHierarchy hierarchy = entryDataStore.retrieveHierarchy();
        hierarchy.getAllLeafClasses().forEach(StatEntry.HIERARCHY::map);
    }

    public StatEntryDataStore getEntryDataStore() {
        return entryDataStore;
    }

    public StatAggregateDataStore getAggregateDataStore() {
        return aggregateDataStore;
    }

    public void record(StatEntry entry) {

        Class<? extends StatEntry> clazz = entry.getClass(); // Will always be a concrete class
        StatEntry.HIERARCHY.map(clazz);

        List<StatEntry> list = entries.getOrDefault(clazz, new ArrayList<>());
        list.add(entry);
        entries.put(clazz, list);
    }

    public void store(long index) {
        for (Map.Entry<Class<? extends StatEntry>, List<StatEntry>> entry : entries.entrySet()) {
            entryDataStore.store(index, entry.getKey(), entry.getValue());
        }
        entries.clear();
    }

    public <E extends StatEntry> List<E> getEntries(Class<E> entryClass, long index) {

        List<Class<? extends E>> baseClasses = StatEntry.HIERARCHY.getLeafClasses(entryClass);

        List<E> entries = new ArrayList<>();

        for (Class<? extends E> baseClass : baseClasses) {
            entries.addAll(entryDataStore.retrieve(baseClass, index));
        }

        return entries;
    }

    public <E extends StatEntry, A> Optional<A> getAggregate(Class<E> entryClass, Class<A> aggregateClass, long index) {
        return aggregateDataStore.retrieve(entryClass, aggregateClass, index);
    }

    public <E extends StatEntry, A> A getOrAggregate(StatAggregator<E, A> aggregator, long index) {
        Class<E> entryClass = aggregator.getEntryClass();
        Class<A> aggregateClass = aggregator.getAggregateClass();

        A aggregate = getAggregate(entryClass, aggregateClass, index).orElse(null);
        if (aggregate == null) {
            List<E> entries = getEntries(entryClass, index);
            aggregate = aggregator.aggregate(entries);
            aggregateDataStore.store(index, entryClass, aggregateClass, aggregate);
        }

        return aggregate;
    }
}
