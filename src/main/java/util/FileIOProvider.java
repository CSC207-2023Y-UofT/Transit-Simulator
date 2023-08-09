package util;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface FileIOProvider {

    /**
     * Writes to a file asynchronously, caching the data to write
     * until the operation completes.
     *
     * @param file The file to write to.
     * @param data The data to write.
     * @return A future that completes when the operation completes. This will
     * complete exceptionally if the operation fails.
     */
    CompletableFuture<Void> write(File file, byte[] data);

    /**
     * Writes a string to a file asynchronously. Equivalent to
     * {@code write(file, data.getBytes())}.
     * @see #write(File, byte[])
     */
    CompletableFuture<Void> writeString(File file, String data);

    /**
     * Read a file synchronously, if the file is cached, the cached data will be returned.
     * @param file The file to read from.
     * @return A byte buffer containing the file data.
     */
    byte[] read(File file) throws IOException;

    String readString(File file) throws IOException;
}
