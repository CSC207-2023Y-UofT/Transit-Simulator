package util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SuppressWarnings("BlockingMethodInNonBlockingContext")
class AsyncWriteIOProviderTest {

    @Test
    void write() throws IOException {
        FileIOProvider ioProvider = new AsyncWriteIOProvider();
        File file = new File("test.txt");
        try {
            Files.delete(file.toPath());
        } catch(IOException ignored) {}
        var future = ioProvider.write(file, "Hello world!".getBytes());
        assert ioProvider.exists(file);

        future.join();
        assert file.exists();
        assert new String(ioProvider.read(file)).equals("Hello world!");
    }

    @Test
    void writeString() throws IOException {
        FileIOProvider ioProvider = new AsyncWriteIOProvider();
        File file = new File("test.txt");
        try {
            Files.delete(file.toPath());
        } catch(IOException ignored) {}
        var future = ioProvider.writeString(file, "Hello world!");
        assert ioProvider.exists(file);

        future.join();
        assert file.exists();
        assert ioProvider.readString(file).equals("Hello world!");
    }

    @Test
    void delete() {
        FileIOProvider ioProvider = new AsyncWriteIOProvider();
        File file = new File("test.txt");
        try {
            Files.delete(file.toPath());
        } catch (IOException ignored) {}
        ioProvider.write(file, "Hello world!".getBytes()).join();
        assert file.exists();
        ioProvider.delete(file).join();
        assert !file.exists();
    }

    @Test
    @SuppressWarnings("ResultOfMethodCallIgnored")
    void listFiles() {
        FileIOProvider ioProvider = new AsyncWriteIOProvider();
        File directory = new File("test-directory");
        directory.mkdirs();
        directory.deleteOnExit();
        File file1 = new File(directory, "test.txt");
        File file2 = new File(directory, "test2.txt");
        ioProvider.write(file1, "Hello world!".getBytes()).join();
        ioProvider.write(file2, "Hello world!".getBytes()).join();

        var files = ioProvider.listFiles(directory);
        assert files.size() == 2;

        assert ioProvider.exists(file1);
        assert ioProvider.exists(file2);

        file1.deleteOnExit();
        file2.deleteOnExit();
    }
}