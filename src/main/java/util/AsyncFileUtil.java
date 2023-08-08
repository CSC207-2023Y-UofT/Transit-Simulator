package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;

/**
 * Utility class for asynchronous file writing.
 * Reads are still performed synchronously, but data may be read from a cache
 * if the file is currently being written to for consistency.
 */
public class AsyncFileUtil {

    /**
     * Represents cached file data.
     */
    private static class CachedFile {

        UUID operationID = UUID.randomUUID();
        File file;
        ByteBuffer data;

        CachedFile(File file, ByteBuffer data) {
            this.file = file;
            this.data = data.duplicate();
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
    private static UUID writeCache(File file, ByteBuffer data) {
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
     * Create a new completion handler that will release the cached file, and
     * close the channel when the operation completes.
     */
    private static CompletionHandler<Integer, Void> newWriteCompletionHandler(
            AsynchronousFileChannel channel,
            File file,
            UUID operationID
    ) {
        Runnable whenDone = () -> {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            writeRelease(file, operationID);
        };
        return new CompletionHandler<>() {
            @Override
            public void completed(Integer result, Void attachment) {
                whenDone.run();
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                whenDone.run();
                exc.printStackTrace();
            }
        };
    }

    /**
     * Write the given data to the given file asynchronously.
     *
     * @param file The file to write to.
     * @param data The data to write.
     * @throws IOException If an I/O error occurs.
     */
    public static void write(File file, ByteBuffer data) throws IOException {
        UUID operationID = writeCache(file, data);
        try {

            AsynchronousFileChannel channel = AsynchronousFileChannel
                    .open(file.toPath(),
                            Set.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE),
                            WRITER);

            channel.write(data, 0, null, newWriteCompletionHandler(channel, file, operationID));

        } catch (Throwable e) {
            writeRelease(file, operationID);
            throw e;
        }
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

    /**
     * Read the given file synchronously. This method may read from the cache
     * if the file is currently being written to.
     *
     * @param file The file to read.
     * @return The data read from the file.
     * @throws IOException If an I/O error occurs.
     */
    public static ByteBuffer read(File file) throws IOException {
        Optional<CachedFile> cachedFile = readCache(file);
        if (cachedFile.isPresent()) return cachedFile.get().data.asReadOnlyBuffer();
        byte[] data = Files.readAllBytes(file.toPath());
        return ByteBuffer.wrap(data).asReadOnlyBuffer();
    }

    /**
     * Utility method to compress data using the deflate algorithm.
     */
    public static byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        byte[] buffer = new byte[1024];
        int length;
        try (var out = new ByteArrayOutputStream()) {
            while (!deflater.finished()) {
                length = deflater.deflate(buffer);
                out.write(buffer, 0, length);
            }
            return out.toByteArray();
        }
    }

    /**
     * Utility method to decompress data using the deflate algorithm.
     */
    public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        var inflater = new java.util.zip.Inflater();
        inflater.setInput(data);
        byte[] buffer = new byte[1024];
        int length;
        try (var out = new ByteArrayOutputStream()) {
            while (!inflater.finished()) {
                length = inflater.inflate(buffer);
                out.write(buffer, 0, length);
            }
            return out.toByteArray();
        }
    }
}
