package persistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import util.FileCompressionProvider;
import util.FileIOProvider;

import static org.junit.jupiter.api.Assertions.*;

class DataStorageTest {

    private FileIOProvider mockIOProvider;
    private FileCompressionProvider mockCompressionProvider;

    @BeforeEach
    void setUp() {
        mockIOProvider = Mockito.mock(FileIOProvider.class);
        mockCompressionProvider = Mockito.mock(FileCompressionProvider.class);
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
