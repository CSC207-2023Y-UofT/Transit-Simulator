package main;

import util.FileCompressionProvider;
import util.FileIOProvider;

public class DataStorage {

    private static FileIOProvider io;
    private static FileCompressionProvider compression;

    public static FileIOProvider getIO() {
        return io;
    }

    public static FileCompressionProvider getCompression() {
        return compression;
    }

    public static void init(FileIOProvider io, FileCompressionProvider compression) {
        DataStorage.io = io;
        DataStorage.compression = compression;
    }

    private DataStorage() {}
}
