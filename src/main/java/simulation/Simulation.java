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

        long lastTick = System.currentTimeMillis() - msPerTick;

        while (true) {

            long delta = System.currentTimeMillis() - lastTick;
            tick((double) delta / msPerTick);

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

        trainSimulator.tick(model, delta);

        if (stats.shouldFlush()) {
            stats.flush();
        }

        if (tickNumber % 50 == 0) {
            pool.getTicketInteractor().cleanTickets();
        }

        tickNumber++;

    }

}
