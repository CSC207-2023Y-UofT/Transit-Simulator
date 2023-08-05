package main;

import interactor.station.IStationInteractor;
import interactor.station.StationInteractor;
import interactor.train.ITrainInteractor;
import interactor.train.TrainInteractor;

/**
 * The InteractorPool dataclass represents a collection of a stationInteractor and a trainInteractor.
 */
public class InteractorPool {
    /**
     * The station interactor.
     */
    private final IStationInteractor stationInteractor;
    /**
     * The train interactor.
     */
    private final ITrainInteractor trainInteractor;

    /**
     * Constructs a new InteractorPool with the given station and train interactors.
     *
     * @param stationInteractor The StationInteractor.
     * @param trainInteractor   The TrainInteractor.
     */
    public InteractorPool(IStationInteractor stationInteractor, ITrainInteractor trainInteractor) {
        this.stationInteractor = stationInteractor;
        this.trainInteractor = trainInteractor;
    }

    /**
     * Gets the station interactor.
     * @return The station interactor.
     */
    public IStationInteractor getStationInteractor() {
        return stationInteractor;
    }

    /**
     * Gets the train interactor.
     * @return The train interactor.
     */
    public ITrainInteractor getTrainInteractor() {
        return trainInteractor;
    }
}
