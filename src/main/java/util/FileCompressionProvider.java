package util;

import java.util.zip.DataFormatException;

public interface FileCompressionProvider {
    byte[] compress(byte[] data);
    byte[] decompress(byte[] data) throws DataFormatException;
}
