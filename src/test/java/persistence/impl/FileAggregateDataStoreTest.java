package persistence.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import persistence.DataStorage;
import stats.aggregate.ExpenseAggregate;
import stats.entry.impl.expense.ElectricityUsageStat;
import util.FileCompressionProvider;
import util.FileIOProvider;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileAggregateDataStoreTest {

    private FileAggregateDataStore fileAggregateDataStore;
    private static final File BASE_PATH = new File("baseDirectory");

    @BeforeEach
    public void setUp() {
        fileAggregateDataStore = new FileAggregateDataStore(BASE_PATH);
    }

    @Test
    public void testStoreAndRetrieve() throws IOException, DataFormatException {
        try (MockedStatic<DataStorage> mockedDataStorage = Mockito.mockStatic(DataStorage.class)) {
            // Stub DataStorage's getIO and getCompression
            FileIOProvider mockFileIOProvider = mock(FileIOProvider.class);
            FileCompressionProvider mockFileCompressionProvider = mock(FileCompressionProvider.class);

            when(DataStorage.getIO()).thenReturn(mockFileIOProvider);
            when(DataStorage.getCompression()).thenReturn(mockFileCompressionProvider);

            // Stub IO methods
            when(mockFileIOProvider.read(any())).thenReturn(new byte[0]);
            when(mockFileCompressionProvider.decompress(any())).thenReturn(new byte[0]);
            when(mockFileCompressionProvider.compress(any())).thenReturn(new byte[0]);

            // Actual test logic
            Map<Long, ExpenseAggregate> expectedAggregates = new HashMap<>();
            expectedAggregates.put(1L, new ExpenseAggregate(1));
            expectedAggregates.put(2L, new ExpenseAggregate(2));

            fileAggregateDataStore.store(1, ElectricityUsageStat.class, ExpenseAggregate.class, new ExpenseAggregate(1));
            fileAggregateDataStore.store(2, ElectricityUsageStat.class, ExpenseAggregate.class, new ExpenseAggregate(2));

            Map<Long, ExpenseAggregate> retrievedAggregates = fileAggregateDataStore.retrieve(1, 2, ElectricityUsageStat.class, ExpenseAggregate.class);

            assertEquals(expectedAggregates, retrievedAggregates);
        }
    }

}
