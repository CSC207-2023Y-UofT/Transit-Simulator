package persistence.impl.memory;

import persistence.boundary.StatEntryDataStore;
import stats.entry.EntryHierarchy;
import stats.entry.StatEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An implementation of StatEntryDataStore that stores stats in memory
 * for testing purposes.
 */
public class MemoryEntryDataStore implements StatEntryDataStore {

    /**
     * Stores the hierarchy of the entries.
     */
    private EntryHierarchy hierarchy = new EntryHierarchy();
    /**
     * Stores the entries.
     */
    private final Map<Class<? extends StatEntry>, Map<Long, List<?>>> data = new HashMap<>();

    @Override
    public <E extends StatEntry> void store(long index, Class<? extends StatEntry> clazz, List<E> entries) {
        Map<Long, List<?>> objectMap = data.computeIfAbsent(clazz, k -> new HashMap<>());
        objectMap.put(index, entries);
    }

    @Override
    public <E extends StatEntry> Map<Long, List<E>> retrieve(long fromIndex, long toIndexInclusive, Class<E> clazz) {
        Map<Long, List<?>> objectMap = data.get(clazz);
        if (objectMap == null) return new HashMap<>();
        Map<Long, List<E>> result = new HashMap<>();
        for (long i = fromIndex; i <= toIndexInclusive; i++) {
            List<?> entries = objectMap.get(i);
            if (entries == null) continue;
            List<E> converted = new ArrayList<>();
            for (Object entry : entries) {
                converted.add(clazz.cast(entry));
            }
            result.put(i, converted);
        }
        return result;
    }

    @Override
    public <E extends StatEntry> Map<Long, List<E>> retrieve(List<Long> indices, Class<E> clazz) {
        Map<Long, List<?>> objectMap = data.get(clazz);
        if (objectMap == null) return new HashMap<>();
        Map<Long, List<E>> result = new HashMap<>();

        for (long index : indices) {
            List<?> entries = objectMap.get(index);
            if (entries == null) continue;
            List<E> converted = new ArrayList<>();
            for (Object entry : entries) {
                converted.add(clazz.cast(entry));
            }
            result.put(index, converted);
        }

        return result;
    }

    @Override
    public void storeHierarchy(EntryHierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    @Override
    public EntryHierarchy retrieveHierarchy() {
        return hierarchy;
    }
}
