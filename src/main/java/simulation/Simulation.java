package simulation;

import model.control.TransitModel;
import model.train.Train;

public class Simulation {
    private final TransitModel model;
    private final TrainSimulator trainSimulator;
    public Simulation(TransitModel model) {
        this.model = model;
        this.trainSimulator = new TrainSimulator();
        createTrains();
    }

    public void createTrains() {
        model.clearTrains();
        // TODO
    }

    public void tick() {
        tickTrains();
    }

    private void tickTrains() {
        trainSimulator.tick(model);
    }

}
