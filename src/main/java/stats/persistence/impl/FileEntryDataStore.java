package stats.persistence.impl;

import stats.entry.StatEntry;
import stats.persistence.StatEntryDataStore;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileEntryDataStore implements StatEntryDataStore {

    private final File directory;

    public FileEntryDataStore(File directory) {
        this.directory = directory;
    }

    private File getFile(long index, Class<? extends StatEntry> clazz) {
        File classFolder = new File(directory, clazz.getSimpleName());
        return new File(classFolder, index + ".stat");
    }

    @Override
    public <E extends StatEntry> void store(long index, Class<? extends StatEntry> clazz, List<E> entries) {
        File file = getFile(index, clazz);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0x00); // Version

        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(entries);
            byte[] bytes = baos.toByteArray();
            Files.write(file.toPath(), bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public <E extends StatEntry> List<E> retrieve(Class<E> clazz, long index) {
        File file = getFile(index, clazz);
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            List<?> entries = (List<?>) new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();

            List<E> castedEntries = new ArrayList<>();
            for (Object entry : entries) {
                // If this fails, then the file is corrupted or the class has changed,
                // but it would probably of failed earlier if the class was changed,
                // so it's probably corrupted.

                // This is just an example branch, so I'm not going to handle it right now
                // but, we would handle it properly if we chose to use this implementation.
                castedEntries.add(clazz.cast(entry));
            }

            return castedEntries;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
