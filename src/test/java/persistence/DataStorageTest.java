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

}
