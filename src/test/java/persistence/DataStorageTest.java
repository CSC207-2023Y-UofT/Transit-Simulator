package persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import util.FileCompressionProvider;
import util.FileIOProvider;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataStorageTest {

    private FileIOProvider mockIOProvider;
    private FileCompressionProvider mockCompressionProvider;
    private File mockFile;

    @BeforeEach
    void setUp() {
        mockIOProvider = Mockito.mock(FileIOProvider.class);
        mockCompressionProvider = Mockito.mock(FileCompressionProvider.class);
        mockFile = Mockito.mock(File.class);
    }

    @Test
    void testInitialization() {
        DataStorage.init(mockIOProvider, mockCompressionProvider);
        assertSame(mockIOProvider, DataStorage.getIO());
        assertSame(mockCompressionProvider, DataStorage.getCompression());
    }

    @Test
    void testGetIOBeforeInitialization() {
        assertNull(DataStorage.getIO());
    }

    @Test
    void testGetCompressionBeforeInitialization() {
        assertNull(DataStorage.getCompression());
    }

    @Test
    void testInitializationWithNullIO() {
        assertThrows(IllegalArgumentException.class, () -> DataStorage.init(null, mockCompressionProvider));
    }

    @Test
    void testInitializationWithNullCompression() {
        assertThrows(IllegalArgumentException.class, () -> DataStorage.init(mockIOProvider, null));
    }

    @Test
    void testInitializationWithBothNull() {
        assertThrows(IllegalArgumentException.class, () -> DataStorage.init(null, null));
    }

    @Test
    void testFileIOProviderReadFails() throws IOException {
        DataStorage.init(mockIOProvider, mockCompressionProvider);
        when(mockIOProvider.read(mockFile)).thenThrow(new IOException());

        assertThrows(IOException.class, () -> mockIOProvider.read(mockFile));
    }

    @Test
    void testFileCompressionFails() throws DataFormatException {
        DataStorage.init(mockIOProvider, mockCompressionProvider);
        when(mockCompressionProvider.decompress("data".getBytes())).thenThrow(new DataFormatException());

        assertThrows(DataFormatException.class, () -> mockCompressionProvider.decompress("data".getBytes()));
    }

    // Helper method to mock a failed future for async operations
    private <T> CompletableFuture<T> failedFuture() {
        CompletableFuture<T> failed = new CompletableFuture<>();
        failed.completeExceptionally(new IOException());
        return failed;
    }

}
