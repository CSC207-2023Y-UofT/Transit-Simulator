package stats.persistence;

import stats.entry.StatEntry;

import java.util.List;

public interface StatEntryDataStore {
    <E extends StatEntry> void store(long index, Class<? extends StatEntry> clazz, List<E> entries);
    <E extends StatEntry> List<E> retrieve(Class<E> clazz, long index); // Clazz may not be an interface
}
