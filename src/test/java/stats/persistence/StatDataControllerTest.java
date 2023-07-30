package stats.persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import stats.aggregator.ExampleExpenseAggregator;
import stats.aggregator.ExpenseAggregate;
import stats.entry.StatEntry;
import stats.entry.impl.MaintenanceStat;
import stats.persistence.impl.FileAggregateDataStore;
import stats.persistence.impl.FileEntryDataStore;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatDataControllerTest {
    private static StatDataController controller;

    @BeforeAll
    static void setup() {
        controller = new StatDataController(
                new FileEntryDataStore(new File("test-entries")),
                new FileAggregateDataStore(new File("test-aggregates"))
        );
        controller.record(new MaintenanceStat());
    }

    @Test
    void record() {
        controller.flush(3902389);
        controller.record(new MaintenanceStat());
        controller.flush(0);
        int size = controller.getEntries(MaintenanceStat.class, 0).size();
        assertEquals(1, size);
    }

    @Test
    void getAggregate() {
        ExampleExpenseAggregator aggregator = new ExampleExpenseAggregator();
        ExpenseAggregate aggregate = aggregator.aggregate(List.of(new MaintenanceStat()));
        controller.getAggregateDataStore().store(0, MaintenanceStat.class, aggregate.getClass(), aggregate);
        ExpenseAggregate aggregate2 = controller.getAggregate(MaintenanceStat.class, ExpenseAggregate.class, 0)
                .orElseThrow();

        assertEquals(aggregate.getExpensesTotal(), aggregate2.getExpensesTotal());
    }

    @Test
    void getOrAggregate() {
    }
}