package simulation;

import main.pool.InteractorPool;
import entity.model.control.TransitModel;
import simulation.api.Simulator;
import stats.StatTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Technically part of the use-case/interactor layer
 * This class performs the basic simulation functionalities needed for the program.
 */
@SuppressWarnings("BlockingMethodInNonBlockingContext")
public class Simulation {

    /**
     * Number of ticks per second
     */
    public static final int TICK_SPEED = 100;

    /**
     * The transit model this object will be running a simulation on
     */
    private final TransitModel model;

    /**
     * The train simulator
     */
    private final List<Simulator> simulators = new ArrayList<>();

    /**
     * Stat data controller
     */
    private final StatTracker stats;

    /**
     * Pool of interactors
     */
    private final InteractorPool pool;

    /**
     * The tick number
     */
    private long tickNumber = 0;

    /**
     * Whether the simulation has started
     */
    private boolean started = false;

    /**
     * Creates a new simulation on the given model.
     *
     * @param model The model to run the simulation on.
     */
    public Simulation(TransitModel model, InteractorPool pool, StatTracker stats) {
        this.model = model;
        this.stats = stats;
        this.pool = pool;
    }

    /**
     * Adds a simulator to the simulation.
     */
    public void addSimulator(Simulator simulator) {
        simulators.add(simulator);
        if (started) {
            simulator.onStart(model);
        }
    }

    /**
     * Starts the simulation.
     */
    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    public void start() {

        started = true;

        simulators.forEach(simulator -> simulator.onStart(model));

        long msPerTick = 1000 / TICK_SPEED;

        long lastTick = System.currentTimeMillis() - msPerTick;

        while (true) {

            long delta = System.currentTimeMillis() - lastTick;
            tick(delta / 1000.0);

            delta = System.currentTimeMillis() - lastTick;
            lastTick = System.currentTimeMillis();

            long sleepTime = msPerTick - delta;
            if (sleepTime <= 0) continue;

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ticks the simulation.
     */
    public void tick(double delta) {

        simulators.forEach(simulator -> simulator.tick(model, delta));

        if (stats.shouldFlush()) {
            stats.flush();
        }

        if (tickNumber % 50 == 0) {
            pool.getTicketInteractor().cleanTickets();
        }

        tickNumber++;

    }

}
