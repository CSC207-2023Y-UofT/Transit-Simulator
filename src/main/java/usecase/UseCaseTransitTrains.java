package usecase;

import model.control.TransitModel;
import model.train.Train;

public class UseCaseTransitTrains {
    private final TransitModel model;

    public UseCaseTransitTrains(TransitModel model) {
        this.model = model;
    }

    public TrainState getTrainState(String trainName) {
        return null;
    }

    private TrainState toState(Train train) {
        String name = train.getName();

    }
}
