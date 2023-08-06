package stats.persistence.impl;

import stats.entry.StatEntry;
import stats.persistence.StatAggregateDataStore;

import java.io.*;
import java.nio.file.Files;
import java.util.Optional;

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
        directory.mkdirs();
        this.directory = directory;
    }

    /**
     * Returns a file instance pointing to the data file for the given index, entry class, and aggregate class.
     *
     * @param index          The index of the data.
     * @param entryClass     The entry class of the data.
     * @param aggregateClass The aggregate class of the data.
     * @return A File instance.
     */
    private File getFile(long index, Class<? extends StatEntry> entryClass, Class<?> aggregateClass) {
        return new File(directory, entryClass.getSimpleName() + "-" + aggregateClass.getSimpleName() + "-" + index);
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
    public synchronized <E extends StatEntry, A> void store(long index, Class<? extends E> entryClass, Class<? extends A> aggregateClass, A aggregate) {
        File file = getFile(index, entryClass, aggregateClass);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream stream = new ObjectOutputStream(baos)) {
            stream.writeObject(aggregate);
            byte[] bytes = baos.toByteArray();
            Files.write(file.toPath(), bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the aggregate value from a file for the given index, entry class, and aggregate class.
     * If no data is found, it returns an empty Optional.
     *
     * @param index          The index of the data.
     * @param entryClass     The entry class of the data.
     * @param aggregateClass The aggregate class of the data.
     * @param <E>            The entry class.
     * @param <A>            The aggregate class.
     * @return An Optional containing the aggregate value if found, or an empty Optional if not found.
     */
    @Override
    public synchronized <E extends StatEntry, A> Optional<A> retrieve(long index, Class<E> entryClass, Class<A> aggregateClass) {
        File file = getFile(index, entryClass, aggregateClass);
        if (!file.exists()) {
            return Optional.empty();
        }

        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            return Optional.ofNullable(aggregateClass.cast(new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject()));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
