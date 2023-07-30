package stats.persistence.impl;

import stats.entry.StatEntry;
import stats.persistence.StatAggregateDataStore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class FileAggregateDataStore implements StatAggregateDataStore {

    private final File directory;

    public FileAggregateDataStore(File directory) {
        directory.mkdirs();
        this.directory = directory;
    }

    private File getFile(long index, Class<? extends StatEntry> entryClass, Class<?> aggregateClass) {
        return new File(directory, entryClass.getSimpleName() + "-" + aggregateClass.getSimpleName() + "-" + index);
    }

    @Override
    public <E extends StatEntry, A> void store(long index, Class<? extends E> entryClass, Class<? extends A> aggregateClass, A aggregate) {
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

    @Override
    public <E extends StatEntry, A> Optional<A> retrieve(Class<E> entryClass, Class<A> aggregateClass, long index) {
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
