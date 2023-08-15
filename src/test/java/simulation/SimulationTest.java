package simulation;

import app_business.interactor.StationInteractor;
import app_business.interactor.TicketInteractor;
import app_business.interactor.TrainInteractor;
import entity.model.control.TransitModel;
import main.pool.InteractorPool;
import org.junit.jupiter.api.Test;
import persistence.impl.memory.MemoryTicketDataStore;
import stats.StatDataControllerImpl;
import stats.StatTracker;

class SimulationTest {

    @Test
    void testEverything() {
        TransitModel model = new TransitModel();
        StatTracker tracker = new StatDataControllerImpl();
        InteractorPool pool = new InteractorPool(
                new StationInteractor(model),
                new TrainInteractor(model),
                new TicketInteractor(new MemoryTicketDataStore(), );
        );
        Simulation simulation = new Simulation(new TransitModel(), null, null);
    }

}