package persistence.impl;

import persistence.DataStorage;
import persistence.PageFileUtils;
import stats.entry.EntryHierarchy;
import stats.entry.StatEntry;
import persistence.boundary.StatEntryDataStore;
import util.Timing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * The page size, i.e. the number of entry lists per file.
     */
    private final int pageSize = 64;

    /**
     * A list of entries.
     */
    private static class EntryList extends ArrayList<StatEntry> {
    }

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
        return new File(classFolder, (index / pageSize) + ".stat");
    }

    /**
     * Returns a file instance pointing to the data file for the given page
     *
     * @param page  The page of the data.
     * @param clazz The class of the data.
     * @return A File instance.
     */
    private File getPageFile(long page, Class<? extends StatEntry> clazz) {
        File classFolder = new File(directory, clazz.getSimpleName());
        classFolder.mkdirs();
        return new File(classFolder, page + ".stat");
    }


    /**
     * Stores the given list of entries into a file for the given index and class.
     *
     * @param index   The index of the data.
     * @param clazz   The class of the data.
     * @param entries The list of entries to store.
     * @param <E>     The class of the entries.
     */
    @Override
    public <E extends StatEntry> void store(long index, Class<? extends StatEntry> clazz, List<E> entries) {
        if (entries.isEmpty()) return;
        File file = getFile(index, clazz);
        Map<Long, EntryList> existingEntries = PageFileUtils.read(file, EntryList.class);
        EntryList list = new EntryList();
        list.addAll(entries);
        existingEntries.put(index, list);
        PageFileUtils.write(file, existingEntries);
    }

    // Inherited javadoc
    @Override
    public <E extends StatEntry> Map<Long, List<E>> retrieve(long from, long to, Class<E> clazz) {
        Map<Long, List<E>> result = new HashMap<>();

        Map<Long, EntryList> currPage = null;
        long currPageNum = -1;

        for (long i = from; i <= to; i++) {
            long page = i / pageSize;
            if (currPage == null || page != currPageNum) {
                currPage = PageFileUtils.read(getPageFile(page, clazz), EntryList.class);
                currPageNum = page;
            }

            List<E> converted = new ArrayList<>();
            for (StatEntry entry : currPage.getOrDefault(i, new EntryList())) {
                converted.add(clazz.cast(entry));
            }

            result.put(i, converted);

        }

        return result;
    }

    // Inherited javadoc
    @Override
    public <E extends StatEntry> Map<Long, List<E>> retrieve(List<Long> indices, Class<E> clazz) throws IOException {

        // Group by page
        Map<Long, List<Long>> byPage = new HashMap<>();

        for (long index : indices) {
            long page = index / pageSize;
            List<Long> pageIndices = byPage.getOrDefault(page, new ArrayList<>());
            pageIndices.add(index);
            byPage.put(page, pageIndices);
        }

        Timing timing = new Timing("retrieve indexed");
        timing.start();
        timing.mark("Pages: " + byPage.keySet().size());

        // Read pages
        Map<Long, List<E>> result = new HashMap<>();
        for (long page : byPage.keySet()) {
            Map<Long, EntryList> pageData = PageFileUtils.read(getPageFile(page, clazz), EntryList.class);
            List<Long> pageIndices = byPage.get(page);
            for (long index : pageIndices) {
                EntryList entries = pageData.getOrDefault(index, new EntryList());
                if (entries.isEmpty()) continue;
                List<E> converted = new ArrayList<>();
                for (StatEntry entry : entries) {
                    converted.add(clazz.cast(entry));
                }
                result.put(index, converted);
            }

            timing.mark("page read");
        }

        return result;
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
        if (!DataStorage.getIO().exists(file)) {
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
