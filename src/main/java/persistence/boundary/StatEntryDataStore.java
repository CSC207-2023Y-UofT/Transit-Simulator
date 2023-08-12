package persistence.boundary;

import stats.entry.EntryHierarchy;
import stats.entry.StatEntry;

import java.util.List;
import java.util.Map;

/**
 * Interface for storing and retrieving stat entries.
 */
public interface StatEntryDataStore {

    /**
     * Store the stat entries {@code entries} at the specified
     * time index {@code index} and under the type {@code clazz}.
     *
     * @param index   The time index at which the stat entries were recorded.
     * @param clazz   The type of stat entries to store.
     * @param entries The stat entries to store.
     * @param <E>     The type of stat entries to store. This should be
     *                a concrete class, not an interface or abstract class.
     */
    <E extends StatEntry> void store(long index, Class<? extends StatEntry> clazz, List<E> entries)
    ;

    /**
     * Retrieve the stat entries that were recorded at the specified
     * time index {@code index} and that are of type {@code clazz}.
     *
     * @param fromIndex        The start of the range of time indices to retrieve.
     * @param toIndexInclusive The end of the range of time indices to retrieve, inclusive.
     * @param clazz            The type of stat entries to retrieve.
     */
    <E extends StatEntry> Map<Long, List<E>> retrieve(long fromIndex, long toIndexInclusive, Class<E> clazz)
    ; // clazz may not be an interface

    /**
     * Retrieve stats from very specific indices. The implementation may
     * try to optimize this operation by grouping requested indices.
     *
     * @param indices The indices to retrieve.
     * @param clazz   The type of stat entries to retrieve.
     * @param <E>     The type of stat entries to retrieve.
     * @return A map of indices to stat entries.
     */
    <E extends StatEntry> Map<Long, List<E>> retrieve(List<Long> indices, Class<E> clazz)
    ; // clazz may not be an interface

    /**
     * Store the entry hierarchy {@code hierarchy}.
     *
     * @param hierarchy The entry hierarchy to store.
     */
    void storeHierarchy(EntryHierarchy hierarchy);

    /**
     * Retrieve the entry hierarchy.
     *
     * @return The entry hierarchy.
     */
    EntryHierarchy retrieveHierarchy();

}
