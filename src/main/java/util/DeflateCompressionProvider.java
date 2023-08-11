package util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Provides file compression using the deflate algorithm.
 */
public class DeflateCompressionProvider implements FileCompressionProvider {

    // Inherited javadoc
    @Override
    public byte[] compress(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        byte[] buffer = new byte[1024];
        int length;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            while (!deflater.finished()) {
                length = deflater.deflate(buffer);
                out.write(buffer, 0, length);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Inherited javadoc
    @Override
    public byte[] decompress(byte[] data) throws DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        byte[] buffer = new byte[1024];
        int length;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            while (!inflater.finished()) {
                length = inflater.inflate(buffer);
                out.write(buffer, 0, length);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
