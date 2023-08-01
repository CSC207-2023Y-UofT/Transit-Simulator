package stats.persistence;

import stats.entry.EntryHierarchy;
import stats.entry.StatEntry;

import java.util.List;

/**
 * Interface for storing and retrieving stat entries.
 */
public interface StatEntryDataStore {

    /**
     * Store the stat entries {@code entries} at the specified
     * time index {@code index} and under the type {@code clazz}.
     * @param index The time index at which the stat entries were recorded.
     * @param clazz The type of stat entries to store.
     * @param entries The stat entries to store.
     * @param <E> The type of stat entries to store. This should be
     *           a concrete class, not an interface or abstract class.
     */
    <E extends StatEntry> void store(long index, Class<? extends StatEntry> clazz, List<E> entries);

    /**
     * Retrieve the stat entries that were recorded at the specified
     * time index {@code index} and that are of type {@code clazz}.
     * @param index The time index at which the stat entries were recorded.
     * @param clazz The type of stat entries to retrieve.
     */
    <E extends StatEntry> List<E> retrieve(long index, Class<E> clazz); // Clazz may not be an interface

    /**
     * Store the entry hierarchy {@code hierarchy}.
     * @param hierarchy The entry hierarchy to store.
     */
    void storeHierarchy(EntryHierarchy hierarchy);

    /**
     * Retrieve the entry hierarchy.
     * @return The entry hierarchy.
     */
    EntryHierarchy retrieveHierarchy();
}
