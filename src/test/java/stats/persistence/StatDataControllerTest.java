package stats.persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import stats.aggregator.expense.ExpenseAggregator;
import stats.aggregate.SingletonAggregate;
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
        SingletonAggregate aggregate = aggregator.aggregate(List.of(new MaintenanceStat(1.0)));
        controller.getAggregateDataStore().store(0, MaintenanceStat.class, aggregate.getClass(), aggregate);
        SingletonAggregate aggregate2 = controller.getAggregate(MaintenanceStat.class, SingletonAggregate.class, 0)
                .orElseThrow();

        assertEquals(aggregate.getTotal(), aggregate2.getTotal());
    }

    @Test
    void getOrAggregate() {
        controller.flush(0);
        for (int i = 0; i < 1000; i++) {
            controller.record(new MaintenanceStat(1.0));
        }
        controller.flush(1);

        ExpenseAggregator aggregator = new ExpenseAggregator();
        SingletonAggregate aggregate = aggregator.aggregate(controller, 1, 1);

        assertEquals(aggregate.getTotal(), 1000.0);
    }
}