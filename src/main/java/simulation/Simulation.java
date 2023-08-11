package simulation;

import main.pool.InteractorPool;
import entity.model.control.TransitModel;
import stats.StatDataController;

/**
 * Technically part of the use-case/interactor layer
 * This class performs the basic simulation functionalities needed for the program.
 */
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
    private final TrainSimulator trainSimulator;

    /**
     * Stat data controller
     */
    private final StatDataController stats;

    /**
     * Pool of interactors
     */
    private final InteractorPool pool;

    /**
     * The tick number
     */
    private long tickNumber = 0;

    /**
     * Creates a new simulation on the given model.
     *
     * @param model The model to run the simulation on.
     */
    public Simulation(TransitModel model, InteractorPool pool, StatDataController stats) {
        this.model = model;
        this.stats = stats;
        this.pool = pool;
        this.trainSimulator = new TrainSimulator(TICK_SPEED, stats);
    }

    /**
     * Starts the simulation.
     */
    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    public void start() {
        this.trainSimulator.recreateTrains(model);

        long msPerTick = 1000 / TICK_SPEED;

        while (true) {
            long ns = System.nanoTime();

            tick();

            long delta = System.nanoTime() - ns;
            long sleepTime = (msPerTick * 1000000) - delta; // TODO get rid of debugging code
            if (sleepTime <= 0) continue;
            try {
                long sleepMs = sleepTime / 1000000;
                int sleepNs = (int) (sleepTime % 1000000);
                Thread.sleep(sleepMs, sleepNs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ticks the simulation.
     */
    public void tick() {

        trainSimulator.tick(model);

        if (stats.shouldFlush()) {
            stats.flush();
        }

        if (tickNumber % 50 == 0) {
            pool.getTicketInteractor().cleanTickets();
        }

        tickNumber++;

    }

}
