package simulation;

import app_business.interactor.EmployeeInteractor;
import app_business.interactor.StationInteractor;
import app_business.interactor.TicketInteractor;
import app_business.interactor.TrainInteractor;
import entity.employee.EmployeeTracker;
import entity.model.control.TransitModel;
import main.pool.InteractorPool;
import org.junit.jupiter.api.Test;
import persistence.impl.memory.MemoryAggregateDataStore;
import persistence.impl.memory.MemoryEntryDataStore;
import persistence.impl.memory.MemoryTicketDataStore;
import stats.StatDataControllerImpl;
import stats.StatTracker;
import stats.timing.BasicTimeIndexingStrategy;

class SimulationTest {

    @Test
    void testEverything() {
        TransitModel model = new TransitModel();
        StatTracker stats = new StatDataControllerImpl(
                new BasicTimeIndexingStrategy(1000),
                new MemoryEntryDataStore(),
                new MemoryAggregateDataStore()
        );
        InteractorPool pool = new InteractorPool(
                new StationInteractor(model),
                new TrainInteractor(model),
                new TicketInteractor(new MemoryTicketDataStore(), stats),
                new EmployeeInteractor(new EmployeeTracker())
                );
        );
        Simulation simulation = new Simulation(new TransitModel(), null, null);
    }

}