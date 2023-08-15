package simulation;

import app_business.interactor.*;
import entity.model.control.TransitModel;
import main.pool.InteractorPool;
import org.junit.jupiter.api.Test;
import persistence.impl.memory.MemoryAggregateDataStore;
import persistence.impl.memory.MemoryEmployeeDataStore;
import persistence.impl.memory.MemoryEntryDataStore;
import persistence.impl.memory.MemoryTicketDataStore;
import stats.StatDataControllerImpl;
import stats.timing.BasicTimeIndexingStrategy;

class SimulationTest {

    @Test
    void testEverything() {
        var model = new TransitModel();

        var stats = new StatDataControllerImpl(
                new BasicTimeIndexingStrategy(1000),
                new MemoryEntryDataStore(),
                new MemoryAggregateDataStore()
        );

        var employeeDataStore = new MemoryEmployeeDataStore();

        InteractorPool pool = new InteractorPool(
                new StationInteractor(model),
                new TrainInteractor(model),
                new TicketInteractor(new MemoryTicketDataStore(), stats),
                new EmployeeInteractor(employeeDataStore, model),
                new StatInteractor(stats)
        );

        Simulation simulation = new Simulation(new TransitModel(), pool, stats);
    }

}