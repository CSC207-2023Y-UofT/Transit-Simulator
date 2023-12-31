package persistence;

import persistence.DataStorage;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

/**
 * A utility class for reading and writing page files that store a map
 * of long keys to a generic type.
 */
@SuppressWarnings("BlockingMethodInNonBlockingContext")
public class PageFileUtils {

    /**
     * Reads a page file from the file system. If there is a class issue with
     * deserialization, an empty map will be returned.
     *
     * @param pageFile  The page file to read.
     * @param typeClass The type of the data.
     * @param <T>       The type of the data.
     * @return The map of data read from the file.
     */
    public static <T> Map<Long, T> read(File pageFile, Class<T> typeClass) {

        try {

            byte[] bytes = DataStorage.getIO().read(pageFile);

            // Decompress the buffer
            bytes = DataStorage.getCompression().decompress(bytes);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);

            // The first 4 bytes make an integer representing the number of elements
            int numElements = buffer.getInt();

            // We will store the keys in a long array
            long[] keys = new long[numElements];

            // The next 8 * numElements bytes are the keys
            LongBuffer keyBuffer = buffer.asLongBuffer();
            keyBuffer.get(keys);

            // Skip the original buffer position past the keys
            buffer.position(buffer.position() + numElements * 8);
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);

            // The remaining bytes are the data
            ByteArrayInputStream inStream = new ByteArrayInputStream(data);
            try (ObjectInputStream ois = new ObjectInputStream(inStream)) {
                Map<Long, T> map = new HashMap<>();
                for (long key : keys) {
                    map.put(key, typeClass.cast(ois.readObject()));
                }
                return map;
            }

        } catch (IOException | ClassNotFoundException | DataFormatException e) {
            return new HashMap<>();
        }
    }

    public static <T> void write(File pageFile, Map<Long, T> map) {

        try {

            // The reverse of the reading process

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            try (ObjectOutputStream oos = new ObjectOutputStream(outStream)) {
                for (Map.Entry<Long, T> entry : map.entrySet()) {
                    oos.writeObject(entry.getValue());
                }
            }

            byte[] entryData = outStream.toByteArray();
            ByteBuffer buffer = ByteBuffer.allocate(4 + 8 * map.size() + entryData.length);
            buffer.putInt(map.size());

            LongBuffer keyBuffer = buffer.asLongBuffer();
            for (long key : map.keySet()) {
                keyBuffer.put(key);
            }

            buffer.position(buffer.position() + map.size() * 8);
            buffer.put(entryData);

            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);

            // Compress
            bytes = DataStorage.getCompression().compress(bytes);

            DataStorage.getIO().write(pageFile, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
