package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Utility class for asynchronous file writing.
 * Reads are still performed synchronously, but data may be read from a cache
 * if the file is currently being written to for consistency.
 */
public class AsyncWriteIOProvider implements FileIOProvider {

    private enum Operation {
        WRITE,
        DELETE
    }

    /**
     * Represents cached file data.
     */
    private static class CachedEntry {

        /**
         * The operation ID for this file.
         */
        final UUID operationID = UUID.randomUUID();

        /**
         * The operation type.
         */
        final Operation operation;

        /**
         * The data, may be null if the file is being deleted.
         */
        final byte[] data;

        /**
         * Constructs a new CachedFile with the given file and data.
         */
        CachedEntry(Operation op, byte[] data) {
            this.data = data;
            this.operation = op;
        }
    }

    /**
     * Lock to facilitate thread safety of the cache.
     */
    private static final ReentrantLock LOCK = new ReentrantLock();

    /**
     * The file cache.
     */
    private static final Map<File, CachedEntry> CACHE = new HashMap<>();

    /**
     * The thread for writing to files. This MUST be a single thread with
     * this implementation, as writes are not otherwise synchronized.
     */
    private static final ExecutorService WRITER = Executors.newSingleThreadExecutor();

    /**
     * Store the given data in the cache for this file
     */
    private static UUID writeCache(File file, byte[] data) {
        LOCK.lock();
        try {
            CachedEntry operation = new CachedEntry(Operation.WRITE, data);
            CACHE.put(file, operation);
            return operation.operationID;
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * Release the cache for this file if the operation ID matches
     */
    private static void writeRelease(File file, UUID operationID) {
        LOCK.lock();
        try {
            CachedEntry cachedEntry = CACHE.get(file);

            if (cachedEntry == null) return;
            if (!cachedEntry.operationID.equals(operationID)) return;

            CACHE.remove(file);

        } finally {
            LOCK.unlock();
        }
    }

    /**
     * Writes the given data to the given file asynchronously.
     */
    public CompletableFuture<Void> write(File file, byte[] data) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        // Ensure atomicity of cache/write operations
        LOCK.lock();
        try {
            UUID operationID = writeCache(file, data);
            WRITER.submit(() -> {
                try {
                    Files.write(file.toPath(), data);
                    future.complete(null);
                } catch (Throwable e) {
                    future.completeExceptionally(e);
                }

                writeRelease(file, operationID);
            });
        } finally {
            LOCK.unlock();
        }

        return future;
    }

    /**
     * Reads the given file from the cache if it is present.
     */
    private static Optional<CachedEntry> readCache(File file) {
        LOCK.lock();
        try {
            CachedEntry cachedEntry = CACHE.get(file);
            if (cachedEntry == null) return Optional.empty();
            return Optional.of(cachedEntry);
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public CompletableFuture<Void> writeString(File file, String data) {
        return write(file, data.getBytes());
    }

    /**
     * Read the given file synchronously. This method may read from the cache
     * if the file is currently being written to.
     *
     * @param file The file to read.
     * @return The data read from the file.
     * @throws IOException If an I/O error occurs.
     */
    public byte[] read(File file) throws IOException {
        Optional<CachedEntry> cachedFile = readCache(file);
        if (cachedFile.isPresent()) {
            CachedEntry entry = cachedFile.get();
            if (entry.operation == Operation.DELETE) {
                throw new FileNotFoundException("File is being deleted");
            }
            byte[] data = cachedFile.get().data;
            return Arrays.copyOf(data, data.length);
        }
        return Files.readAllBytes(file.toPath());
    }

    // Inherited javadoc
    @Override
    public String readString(File file) throws IOException {
        return new String(read(file));
    }

    // Inherited javadoc
    @Override
    public boolean exists(File file) {
        LOCK.lock();
        try {
             CachedEntry cachedEntry = CACHE.get(file);
             if (cachedEntry == null) return file.exists();
             return cachedEntry.operation == Operation.WRITE;
        } finally {
            LOCK.unlock();
        }
    }

    private void deleteCache(File file) {
        LOCK.lock();
        try {
            CACHE.put(file, new CachedEntry(Operation.DELETE, null));
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public CompletableFuture<Void> delete(File file) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        // Ensure atomicity of cache/write operations
        LOCK.lock();
        try {
            deleteCache(file);
            WRITER.submit(() -> {
                try {
                    Files.delete(file.toPath());
                    future.complete(null);
                } catch (NoSuchFileException ignored) {
                    future.complete(null);
                } catch (Throwable e) {
                    future.completeExceptionally(e);
                }
            });
        } finally {
            LOCK.unlock();
        }

        return future;
    }

    @Override
    public List<File> listFiles(File directory) {

        Set<File> files = new HashSet<>();
        File[] filesArray = directory.listFiles();

        if (filesArray != null) {
            files.addAll(Arrays.asList(filesArray));
        }

        LOCK.lock();
        try {
            for (Map.Entry<File, CachedEntry> entry : CACHE.entrySet()) {
                File cacheFile = entry.getKey();
                if (directory.equals(cacheFile.getParentFile())) {
                    if (entry.getValue().operation == Operation.DELETE) {
                        files.remove(cacheFile);
                    } else if (entry.getValue().operation == Operation.WRITE) {
                        files.add(cacheFile);
                    }
                }
            }
        } finally {
            LOCK.unlock();
        }

        return new ArrayList<>(files);
    }
}
