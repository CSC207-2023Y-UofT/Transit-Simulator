package main;

import util.FileCompressionProvider;
import util.FileIOProvider;

/**
 * The {@code DataStorage} class provides static methods to manage
 * and access file I/O and file compression functionalities.
 * <p>
 * This class should be initialized with implementations of
 * {@link FileIOProvider} and {@link FileCompressionProvider}
 * before accessing any of the get methods.
 * <p>
 * As a utility class, it is not designed to be instantiated.
 */
public class DataStorage {

    /** The file I/O provider. */
    private static FileIOProvider io;

    /** The file compression provider. */
    private static FileCompressionProvider compression;

    /**
     * Returns the current file I/O provider.
     *
     * @return the current file I/O provider
     */
    public static FileIOProvider getIO() {
        return io;
    }

    /**
     * Returns the current file compression provider.
     *
     * @return the current file compression provider
     */
    public static FileCompressionProvider getCompression() {
        return compression;
    }

    /**
     * Initializes the data storage with the provided file I/O and
     * file compression providers.
     *
     * @param io           the file I/O provider to set
     * @param compression  the file compression provider to set
     */
    public static void init(FileIOProvider io, FileCompressionProvider compression) {
        DataStorage.io = io;
        DataStorage.compression = compression;
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private DataStorage() {}
}
