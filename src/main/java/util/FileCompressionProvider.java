package util;

import java.util.zip.DataFormatException;

/**
 * Provides file compression using the deflate algorithm.
 */
public interface FileCompressionProvider {

    /**
     * Compresses a byte array using the deflate algorithm.
     * @param data The data to compress.
     * @return The compressed data.
     */
    byte[] compress(byte[] data);

    /**
     * Decompresses a byte array using the inflate algorithm.
     * @param data The data to decompress.
     * @return The decompressed data.
     * @throws DataFormatException If the data is not in a valid format.
     */
    byte[] decompress(byte[] data) throws DataFormatException;
}
