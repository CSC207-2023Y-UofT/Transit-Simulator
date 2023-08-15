package persistence.impl.memory;

import org.junit.jupiter.api.Test;
import stats.aggregate.ExpenseAggregate;
import stats.entry.impl.expense.MaintenanceStat;

/**
 * Test for {@link MemoryAggregateDataStore}
 */
class MemoryAggregateDataStoreTest {

    /**
     * Data store
     */
    private static final MemoryAggregateDataStore STORE = new MemoryAggregateDataStore();

    @Test
    void store() {
        var agg = new ExpenseAggregate(1.0);
        STORE.store(1, MaintenanceStat.class, ExpenseAggregate.class, agg);
        assert STORE.retrieve(1, 1, MaintenanceStat.class, ExpenseAggregate.class).size() == 1;
    }

    @Test
    void retrieve() {
        var agg = new ExpenseAggregate(1.0);
        STORE.store(1, MaintenanceStat.class, ExpenseAggregate.class, agg);
        assert STORE.retrieve(1, 1, MaintenanceStat.class, ExpenseAggregate.class).size() == 1;
    }
}