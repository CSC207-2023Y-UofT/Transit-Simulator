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
//        createTrains();
    }


    public void tick() {
        trainSimulator.tick(model);
    }

}
