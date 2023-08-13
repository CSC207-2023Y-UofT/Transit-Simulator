package util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Provides file IO operations asynchronously. As well as
 * to synchronize IO operations across the application.
 */
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

    /**
     * Read a file synchronously, if the file is cached, the cached data will be returned.
     * @param file The file to read from.
     * @return A string containing the file data.
     */
    String readString(File file) throws IOException;

    /**
     * Checks if a file exists, or is cached
     */
    boolean exists(File file);

    CompletableFuture<Void> delete(File file);

    /**
     * List a list of files in the specified directory of which
     * can likely immediately be read with this IO provider.
     */
    List<File> listFiles(File directory);
}
