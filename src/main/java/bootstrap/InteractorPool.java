package bootstrap;

import interactor.station.StationInteractor;
import interactor.train.TrainInteractor;

/**
 * The InteractorPool dataclass represents a collection of a stationInteractor and a trainInteractor.
 */
public class InteractorPool {
    /**
     * The station interactor.
     */
    private final StationInteractor stationInteractor;
    /**
     * The train interactor.
     */
    private final TrainInteractor trainInteractor;

    /**
     * Constructs a new InteractorPool with the given station and train interactors.
     *
     * @param stationInteractor The StationInteractor.
     * @param trainInteractor   The TrainInteractor.
     */
    public InteractorPool(StationInteractor stationInteractor, TrainInteractor trainInteractor) {
        this.stationInteractor = stationInteractor;
        this.trainInteractor = trainInteractor;
    }

    /**
     * Gets the station interactor.
     * @return The station interactor.
     */
    public StationInteractor getStationInteractor() {
        return stationInteractor;
    }

    /**
     * Gets the train interactor.
     * @return The train interactor.
     */
    public TrainInteractor getTrainInteractor() {
        return trainInteractor;
    }
}
