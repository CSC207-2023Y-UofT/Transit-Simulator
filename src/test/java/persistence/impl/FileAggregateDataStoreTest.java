package persistence.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.DataStorage;
import stats.aggregate.ExpenseAggregate;
import stats.entry.impl.expense.ElectricityUsageStat;
import util.DeflateCompressionProvider;
import util.FileIOProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileAggregateDataStoreTest {

    private FileAggregateDataStore fileAggregateDataStore;
    private static final File BASE_PATH = new File("test-file-aggr-dir");

    @BeforeEach
    public void setUp() {
        fileAggregateDataStore = new FileAggregateDataStore(BASE_PATH);

        DataStorage.init(
                new FileIOProvider() {

                    private final Map<File, byte[]> data = new HashMap<>();

                    @Override
                    public CompletableFuture<Void> write(File file, byte[] data) {
                        this.data.put(file, data);
                        return CompletableFuture.completedFuture(null);
                    }

                    @Override
                    public CompletableFuture<Void> writeString(File file, String data) {
                        return write(file, data.getBytes());
                    }

                    @Override
                    public byte[] read(File file) throws IOException {
                        if (!exists(file)) {
                            throw new NoSuchFileException("No such file");
                        }
                        return data.get(file);
                    }

                    @Override
                    public String readString(File file) throws IOException {
                        return new String(read(file));
                    }

                    @Override
                    public boolean exists(File file) {
                        return data.containsKey(file);
                    }

                    @Override
                    public CompletableFuture<Void> delete(File file) {
                        data.remove(file);
                        return CompletableFuture.completedFuture(null);
                    }

                    @Override
                    public List<File> listFiles(File directory) {
                        return null;
                    }
                },
                new DeflateCompressionProvider()
        );
    }

    @Test
    public void testStoreAndRetrieve() {

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
