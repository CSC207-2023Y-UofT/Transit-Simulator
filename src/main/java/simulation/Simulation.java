package simulation;

import model.control.TransitModel;
import model.train.Train;

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
     * Creates a new simulation on the given model.
     * @param model The model to run the simulation on.
     */
    public Simulation(TransitModel model) {
        this.model = model;
        this.trainSimulator = new TrainSimulator(TICK_SPEED);
    }

    /**
     * Starts the simulation.
     */
    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    public void start() {
        this.trainSimulator.recreateTrains(model);

        long msPerTick = 1000 / TICK_SPEED;

        while (true) {
            long ms = System.currentTimeMillis();

            tick();

            long delta = System.currentTimeMillis() - ms;
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
    public void tick() {
        trainSimulator.tick(model);
    }

}
