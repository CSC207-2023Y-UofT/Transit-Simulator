package simulation;

import app_business.interactor.*;
import entity.model.control.TransitModel;
import entity.model.control.builder.TransitModelBuilder;
import main.pool.InteractorPool;
import org.junit.jupiter.api.Test;
import persistence.impl.memory.MemoryAggregateDataStore;
import persistence.impl.memory.MemoryEmployeeDataStore;
import persistence.impl.memory.MemoryEntryDataStore;
import persistence.impl.memory.MemoryTicketDataStore;
import simulation.api.Simulator;
import simulation.simulators.TrainSimulator;
import stats.StatDataControllerImpl;
import stats.aggregator.impl.ExpenseAggregator;
import stats.timing.BasicTimeIndexingStrategy;

import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("BlockingMethodInNonBlockingContext")
class SimulationTest {

    @Test
    void testEverything() {
        var modelBuilder = new TransitModelBuilder();
        modelBuilder.station("A", 1, 1);
        modelBuilder.station("B", 2, 2);
        modelBuilder.station("C", 3, 3);
        modelBuilder.station("D", 4, 4);

        modelBuilder.line(1, "A", "B");

        var model = modelBuilder.build();

        var stats = new StatDataControllerImpl(
                new BasicTimeIndexingStrategy(999999),
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

        assert stats.aggregateCurrent(new ExpenseAggregator())
                .isPresent();

    }

}