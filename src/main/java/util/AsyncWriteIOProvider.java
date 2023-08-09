package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

    /**
     * Represents cached file data.
     */
    private static class CachedFile {

        /**
         * The operation ID for this file.
         */
        UUID operationID = UUID.randomUUID();

        /**
         * The file.
         */
        File file;

        /**
         * The data.
         */
        byte[] data;

        /**
         * Constructs a new CachedFile with the given file and data.
         */
        CachedFile(File file, byte[] data) {
            this.file = file;
            this.data = data;
        }
    }

    /**
     * Lock to facilitate thread safety of the cache.
     */
    private static final ReentrantLock LOCK = new ReentrantLock();

    /**
     * The file cache.
     */
    private static final Map<File, CachedFile> CACHE = new HashMap<>();

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
            CachedFile cachedFile = new CachedFile(file, data);
            CACHE.put(file, cachedFile);
            return cachedFile.operationID;
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
            CachedFile cachedFile = CACHE.get(file);

            if (cachedFile == null) return;
            if (!cachedFile.operationID.equals(operationID)) return;

            CACHE.remove(file);

        } finally {
            LOCK.unlock();
        }
    }

    /**
     * Writes the given data to the given file asynchronously.
     */
    public CompletableFuture<Void> write(File file, byte[] data) {
        UUID operationID = writeCache(file, data);
        CompletableFuture<Void> future = new CompletableFuture<>();

        WRITER.submit(() -> {
            try {
                Files.write(file.toPath(), data);
                future.complete(null);
            } catch (Throwable e) {
                future.completeExceptionally(e);
            }

            writeRelease(file, operationID);
        });

        return future;
    }

    /**
     * Reads the given file from the cache if it is present.
     */
    private static Optional<CachedFile> readCache(File file) {
        LOCK.lock();
        try {
            CachedFile cachedFile = CACHE.get(file);
            if (cachedFile == null) return Optional.empty();
            return Optional.of(cachedFile);
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
        Optional<CachedFile> cachedFile = readCache(file);
        if (cachedFile.isPresent()) {
            byte[] data = cachedFile.get().data;
            return Arrays.copyOf(data, data.length);
        }
        return Files.readAllBytes(file.toPath());
    }

    @Override
    public String readString(File file) throws IOException {
        return new String(read(file));
    }

    @Override
    public boolean exists(File file) {
        boolean cached;
        LOCK.lock();
        try {
             cached = CACHE.containsKey(file);
        } finally {
            LOCK.unlock();
        }
        return cached || file.exists();
    }

}
