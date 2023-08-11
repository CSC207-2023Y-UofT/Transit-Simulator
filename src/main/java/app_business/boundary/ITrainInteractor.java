package app_business.boundary;

import app_business.dto.TrainDTO;

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
    Optional<TrainDTO> find(String trainName);

    /**
     * Gets the state of the next train in the given direction
     *
     * @return The state of the next train in the given direction
     * @see TrainDTO
     */
    List<TrainDTO> getTrains();

    /**
     * Sets the needs maintenance flag of a train
     *
     * @param trainName        The name of the train to set the needs maintenance flag of
     * @param needsMaintenance The value to set the needs maintenance flag to
     */
    void setNeedsMaintenance(String trainName, boolean needsMaintenance);
}
