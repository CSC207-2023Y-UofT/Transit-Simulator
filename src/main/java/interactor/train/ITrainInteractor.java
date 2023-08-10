package interactor.train;

import java.util.List;
import java.util.Optional;

/**
 * Input boundary for train interactor
 */
public interface ITrainInteractor {
    /**
     * Gets the state of a train
     *
     * @param trainName The name of the train to get the state of
     * @return The state of the train
     * @see TrainDTO
     */
    Optional<TrainDTO> getTrain(String trainName);

    /**
     * Gets the state of the next train in the given direction
     *
     * @return The state of the next train in the given direction
     * @see TrainDTO
     */
    List<TrainDTO> getTrains();
}
