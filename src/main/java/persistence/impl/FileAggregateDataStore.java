package persistence.impl;

import persistence.PageFileUtils;
import stats.entry.StatEntry;
import persistence.boundary.StatAggregateDataStore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * A file-based implementation of the {@link StatAggregateDataStore} interface.
 * This class provides methods to store and retrieve aggregated statistic data from a file system.
 */
public class FileAggregateDataStore implements StatAggregateDataStore {

    /**
     * The directory where the aggregated data files are stored.
     */
    private final File directory;

    /**
     * Constructs a FileAggregateDataStore instance with a specified directory.
     *
     * @param directory The directory where the aggregated data files are stored.
     */
    public FileAggregateDataStore(File directory) {
        if (!directory.mkdirs()) {
            throw new RuntimeException("Failed to create directory: " + directory);
        }
        this.directory = directory;
    }

    /**
     * Returns a file instance pointing to the data file for the given page, entry class, and aggregate class.
     *
     * @param page           The page of the data.
     * @param entryClass     The entry class of the data.
     * @param aggregateClass The aggregate class of the data.
     * @return A File instance.
     */
    private File getFile(long page, Class<? extends StatEntry> entryClass, Class<?> aggregateClass) {
        return new File(directory, entryClass.getSimpleName() + "-" + aggregateClass.getSimpleName() + "-" + page);
    }

    /**
     * Read a page of data from the file system.
     *
     * @param pageNumber     The page number to read.
     * @param entryClass     The entry class.
     * @param aggregateClass The aggregate class.
     * @param <E>            The entry class.
     * @param <A>            The aggregate class.
     * @return The map of data read from the file.
     */
    private synchronized <E extends StatEntry, A> Map<Long, A> read(long pageNumber, Class<E> entryClass, Class<A> aggregateClass) {
        File file = getFile(pageNumber, entryClass, aggregateClass);
        return PageFileUtils.read(file, aggregateClass);
    }

    /**
     * Write a page of data to the file system.
     *
     * @param pageNumber     The page number to write.
     * @param entryClass     The entry class.
     * @param aggregateClass The aggregate class.
     * @param map            The map of data to write.
     * @param <E>            The entry class.
     * @param <A>            The aggregate class.
     */
    private <E extends StatEntry, A> void write(long pageNumber, Class<E> entryClass, Class<A> aggregateClass, Map<Long, A> map) {
        File file = getFile(pageNumber, entryClass, aggregateClass);
        PageFileUtils.write(file, map);
    }

    private long toPage(long index) {
        int pageSize = 256;
        return index / pageSize;
    }

    /**
     * Retrieves the aggregate value from a file for the given index, entry class, and aggregate class.
     * If no data is found, it returns an empty Optional.
     *
     * @param startIndex     The start index of the data.
     * @param endIndex       The end index of the data, inclusive.
     * @param entryClass     The entry class of the data.
     * @param aggregateClass The aggregate class of the data.
     * @param <E>            The entry class.
     * @param <A>            The aggregate class.
     * @return An Optional containing the aggregate value if found, or an empty Optional if not found.
     */
    @Override
    public synchronized <E extends StatEntry, A> Map<Long, A> retrieve(long startIndex, long endIndex, Class<E> entryClass, Class<A> aggregateClass) {
        long startPage = toPage(startIndex);
        long endPage = toPage(endIndex);

        Map<Long, A> map = new HashMap<>();

        for (long page = startPage; page <= endPage; page++) {
            Map<Long, A> pageMap = read(page, entryClass, aggregateClass);
            for (long index : pageMap.keySet()) {
                if (index >= startIndex && index <= endIndex) {
                    map.put(index, pageMap.get(index));
                }
            }
        }

        return map;
    }

    /**
     * Stores the given aggregate value into a file for the given index, entry class, and aggregate class.
     *
     * @param index          The index of the data.
     * @param entryClass     The entry class of the data.
     * @param aggregateClass The aggregate class of the data.
     * @param aggregate      The aggregate value to store.
     * @param <E>            The entry class.
     * @param <A>            The aggregate class.
     */
    @Override
    public synchronized <E extends StatEntry, A> void store(long index, Class<E> entryClass, Class<A> aggregateClass, A aggregate) {
        long page = toPage(index);
        Map<Long, A> map = read(page, entryClass, aggregateClass);
        map.put(index, aggregate);
        write(page, entryClass, aggregateClass, map);
    }

}
