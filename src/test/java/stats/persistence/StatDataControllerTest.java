package stats.persistence;

import persistence.DataStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import stats.StatDataController;
import stats.aggregator.expense.ExpenseAggregate;
import stats.aggregator.expense.ExpenseAggregator;
import stats.entry.impl.MaintenanceStat;
import persistence.impl.FileAggregateDataStore;
import persistence.impl.FileEntryDataStore;
import util.AsyncWriteIOProvider;
import util.DeflateCompressionProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatDataControllerTest {
    private static StatDataController controller;


    private static void deleteDirectory(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files)
                    deleteDirectory(f);
            }
        }
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    static void setup() {

        DataStorage.init(
                new AsyncWriteIOProvider(),
                new DeflateCompressionProvider()
        );

        File entryFolder = new File("test-entries");
        File aggregateFolder = new File("test-aggregates");
        deleteDirectory(entryFolder);
        deleteDirectory(aggregateFolder);

        controller = new StatDataController(
                new FileEntryDataStore(entryFolder),
                new FileAggregateDataStore(aggregateFolder)
        );

        controller.record(new MaintenanceStat(1.0));
    }

    @Test
    void record() {
        controller.flush(3902389);
        controller.record(new MaintenanceStat(1.0));
        controller.flush(0);
        int size = controller.getEntries(MaintenanceStat.class, 0).size();
        assertEquals(1, size);
    }

    @Test
    void getAggregate() {
        ExpenseAggregator aggregator = new ExpenseAggregator();
        ExpenseAggregate aggregate = aggregator.aggregate(List.of(new MaintenanceStat(1.0)));
        try {
            controller.getAggregateDataStore().store(0, MaintenanceStat.class, ExpenseAggregate.class, aggregate);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ExpenseAggregate aggregate2 = controller.getAggregates(MaintenanceStat.class, ExpenseAggregate.class, 0, 0)
                .get(0L);
        assertNotNull(aggregate2);
        assertEquals(aggregate.getValue(), aggregate2.getValue());
    }

    @Test
    void getOrAggregate() {
        controller.flush(0);
        for (int i = 0; i < 1000; i++) {
            controller.record(new MaintenanceStat(1.0));
        }
        controller.flush(2);

        ExpenseAggregator aggregator = new ExpenseAggregator();

        ExpenseAggregate aggregate = aggregator.aggregate(controller, 2, 2)
                .orElse(new ExpenseAggregate(0.0));

        assertEquals(1000.0, aggregate.getValue());
    }
}