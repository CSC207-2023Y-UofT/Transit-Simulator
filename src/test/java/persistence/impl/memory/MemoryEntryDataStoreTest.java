package persistence.impl.memory;

import main.Main;
import org.junit.jupiter.api.Test;
import stats.entry.impl.expense.MaintenanceStat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link MemoryEntryDataStore}
 */
class MemoryEntryDataStoreTest {

    /**
     * Data store
     */
    private static final MemoryEntryDataStore STORE = new MemoryEntryDataStore();


    @Test
    void store() {
        var entry = new MaintenanceStat();
        STORE.store(1, MaintenanceStat.class, List.of(entry));
        assert STORE.retrieve(1, 1, MaintenanceStat.class).size() == 1;
    }

    @Test
    void retrieve() {
        var entry = new MaintenanceStat();
        STORE.store(1, MaintenanceStat.class, List.of(entry));
        assert STORE.retrieve(1, 1, MaintenanceStat.class).size() == 1;
    }

}