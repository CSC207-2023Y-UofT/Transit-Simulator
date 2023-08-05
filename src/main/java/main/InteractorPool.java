package main;

import interactor.station.IStationInteractor;
import interactor.station.StationInteractor;
import interactor.train.ITrainInteractor;
import interactor.train.TrainInteractor;

public class InteractorPool {
    private final IStationInteractor stationInteractor;
    private final ITrainInteractor trainInteractor;

    public InteractorPool(IStationInteractor stationInteractor, ITrainInteractor trainInteractor) {
        this.stationInteractor = stationInteractor;
        this.trainInteractor = trainInteractor;
    }

    public IStationInteractor getStationInteractor() {
        return stationInteractor;
    }

    public ITrainInteractor getTrainInteractor() {
        return trainInteractor;
    }
}
