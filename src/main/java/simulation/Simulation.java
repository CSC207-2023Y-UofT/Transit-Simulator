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

    public Simulation(TransitModel model) {
        this.model = model;
        this.trainSimulator = new TrainSimulator(TICK_SPEED);
    }

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


    public void tick() {
        trainSimulator.tick(model);
    }

}
