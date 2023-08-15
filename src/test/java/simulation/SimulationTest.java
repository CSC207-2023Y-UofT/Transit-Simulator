package simulation;

import app_business.interactor.*;
import entity.model.control.TransitModel;
import main.pool.InteractorPool;
import org.junit.jupiter.api.Test;
import persistence.impl.memory.MemoryAggregateDataStore;
import persistence.impl.memory.MemoryEmployeeDataStore;
import persistence.impl.memory.MemoryEntryDataStore;
import persistence.impl.memory.MemoryTicketDataStore;
import simulation.api.Simulator;
import simulation.simulators.TrainSimulator;
import stats.StatDataControllerImpl;
import stats.timing.BasicTimeIndexingStrategy;

import java.util.concurrent.atomic.AtomicBoolean;

class SimulationTest {

    @Test
    void testEverything() {
        var model = new TransitModel();

        model.

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

        var started = new AtomicBoolean(false);
        var ticked = new AtomicBoolean(false);

        Simulator simulator = new Simulator() {

            @Override
            public void onStart(TransitModel model) {
                started.set(true);
            }

            @Override
            public void tick(TransitModel model, double deltaTime) {
                ticked.set(true);
            }
        };

        simulation.addSimulator(simulator);
        simulation.addSimulator(new TrainSimulator(stats));

        var thread = new Thread(simulation::start);
        thread.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        simulation.stop();

        assert started.get();
        assert ticked.get();

    }

}