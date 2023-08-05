package main;

import interactor.station.StationInteractor;
import interactor.train.TrainInteractor;

public class InteractorPool {
    private final StationInteractor stationInteractor;
    private final TrainInteractor trainInteractor;

    public InteractorPool(StationInteractor stationInteractor, TrainInteractor trainInteractor) {
        this.stationInteractor = stationInteractor;
        this.trainInteractor = trainInteractor;
    }

    public StationInteractor getStationInteractor() {
        return stationInteractor;
    }

    public TrainInteractor getTrainInteractor() {
        return trainInteractor;
    }
}
