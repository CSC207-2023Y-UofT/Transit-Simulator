package stats.persistence.impl;

import stats.entry.EntryHierarchy;
import stats.entry.StatEntry;
import stats.persistence.StatEntryDataStore;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * A file-based implementation of the {@link StatEntryDataStore} interface.
 * This class provides methods to store and retrieve statistic entry data from a file system.
 */
public class FileEntryDataStore implements StatEntryDataStore {

    /**
     * The directory where the entry data files are stored.
     */
    private final File directory;

    /**
     * Constructs a FileEntryDataStore instance with a specified directory.
     *
     * @param directory The directory where the entry data files are stored.
     */
    public FileEntryDataStore(File directory) {
        directory.mkdirs();
        this.directory = directory;
    }

    /**
     * Returns a file instance pointing to the data file for the given index and class.
     *
     * @param index The index of the data.
     * @param clazz The class of the data.
     * @return A File instance.
     */
    private File getFile(long index, Class<? extends StatEntry> clazz) {
        File classFolder = new File(directory, clazz.getSimpleName());
        classFolder.mkdirs();
        return new File(classFolder, index + ".stat");
    }


    /**
     * Stores the given list of entries into a file for the given index and class.
     *
     * @param index The index of the data.
     * @param clazz The class of the data.
     * @param entries The list of entries to store.
     * @param <E> The class of the entries.
     */
    @Override
    public <E extends StatEntry> void store(long index, Class<? extends StatEntry> clazz, List<E> entries) {
        File file = getFile(index, clazz);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(0x00); // Version

        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(entries);
            byte[] bytes = baos.toByteArray();
            Files.write(file.toPath(), bytes, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Retrieves a list of entries from a file for the given index and class.
     * If no data is found, it returns an empty list.
     *
     * @param index The index of the data.
     * @param clazz The class of the data.
     * @param <E> The class of the entries.
     * @return A list of entries.
     */
    @Override
    public <E extends StatEntry> List<E> retrieve(long index, Class<E> clazz) {
        File file = getFile(index, clazz);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            byte[] bytes = Files.readAllBytes(file.toPath());
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            int version = bais.read(); // Version
            List<?> entries = (List<?>) new ObjectInputStream(bais).readObject();

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

    /**
     * Stores an EntryHierarchy into the file system.
     *
     * @param hierarchy The EntryHierarchy to store.
     */
    @Override
    public void storeHierarchy(EntryHierarchy hierarchy) {
        List<Class<? extends StatEntry>> leafClasses = hierarchy.getAllLeafClasses();
        List<String> classNames = new ArrayList<>();
        for (Class<? extends StatEntry> leafClass : leafClasses) {
            classNames.add(leafClass.getName());
        }

        File file = new File(directory, "hierarchy-leaves.txt");
        try {
            Files.write(file.toPath(), classNames);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Retrieves an EntryHierarchy from the file system.
     * If no data is found, it returns a new EntryHierarchy.
     *
     * @return An EntryHierarchy.
     */
    @Override
    public EntryHierarchy retrieveHierarchy() {
        File file = new File(directory, "hierarchy-leaves.txt");
        if (!file.exists()) {
            return new EntryHierarchy();
        }
        try {
            List<String> classNames = Files.readAllLines(file.toPath());

            List<Class<? extends StatEntry>> classes = new ArrayList<>();
            for (String className : classNames) {
                try {
                    classes.add(Class.forName(className).asSubclass(StatEntry.class));
                } catch (ClassNotFoundException | ClassCastException e) {
                    // Ignored, the class was removed. This doesn't matter.
                }
            }

            EntryHierarchy hierarchy = new EntryHierarchy();
            classes.forEach(hierarchy::map);
            return hierarchy;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
