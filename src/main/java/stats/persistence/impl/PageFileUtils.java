package stats.persistence.impl;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class PageFileUtils {

    public static <T> Map<Long, T> read(File pageFile, Class<T> typeClass) {

        if (!pageFile.exists()) {
            return new HashMap<>();
        }

        try {
            byte[] bytes = Files.readAllBytes(pageFile.toPath());
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            int numElements = buffer.getInt();
            long[] keys = new long[numElements];
            LongBuffer keyBuffer = buffer.asLongBuffer();
            keyBuffer.get(keys);

            buffer.position(buffer.position() + numElements * 8);
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);

            ByteArrayInputStream inStream = new ByteArrayInputStream(data);
            try (ObjectInputStream ois = new ObjectInputStream(inStream)) {
                Map<Long, T> map = new HashMap<>();
                for (long key : keys) {
                    map.put(key, typeClass.cast(ois.readObject()));
                }
                return map;
            }

        } catch (IOException | ClassNotFoundException e) {
            return new HashMap<>();
        }
    }

    public static <T> void write(File pageFile, Map<Long, T> map) {

        try {
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

            Files.write(pageFile.toPath(), bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
