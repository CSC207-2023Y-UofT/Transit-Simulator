package persistence.impl.memory;

import persistence.boundary.StatAggregateDataStore;
import stats.entry.StatEntry;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of StatAggregateDataStore that stores stats in memory
 * for testing purposes.
 */
public class MemoryAggregateDataStore implements StatAggregateDataStore {

    /**
     * The giant map used to store the data.
     */
    private final Map<Class<?>, Map<Class<?>, Map<Long, Object>>> data = new HashMap<>();

    @Override
    public <E extends StatEntry, A> void store(long index, Class<E> entryClass, Class<A> aggregateClass, A aggregate) {
        Map<Class<?>, Map<Long, Object>> entryMap = data.computeIfAbsent(entryClass, k -> new HashMap<>());
        Map<Long, Object> aggregateMap = entryMap.computeIfAbsent(aggregateClass, k -> new HashMap<>());
        aggregateMap.put(index, aggregate);
    }

    @Override
    public <E extends StatEntry, A> Map<Long, A> retrieve(long startIndex, long endIndex, Class<E> entryClass, Class<A> aggregateClass) {
        Map<Class<?>, Map<Long, Object>> entryMap = data.get(entryClass);
        if (entryMap == null) return new HashMap<>();
        Map<Long, Object> aggregateMap = entryMap.get(aggregateClass);
        if (aggregateMap == null) return new HashMap<>();
        Map<Long, A> result = new HashMap<>();
        for (long i = startIndex; i <= endIndex; i++) {
            A aggregate = aggregateClass.cast(aggregateMap.get(i));
            if (aggregate == null) continue;
            result.put(i, aggregate);
        }
        return result;
    }
}
