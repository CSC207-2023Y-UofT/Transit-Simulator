package interactor.train;

import java.util.List;

/**
 * Defines an interface for train-related interactions, specifically for retrieving
 * the state of individual trains and a list of all train states.
 */
public interface ITrainInteractor {

    /**
     * Fetches the current state of a specified train by its name.
     *
     * @param trainName The name of the train whose state is to be fetched.
     * @return The current state of the specified train.
     */
    TrainState getTrainState(String trainName);

    /**
     * Retrieves a list containing the current states of all trains.
     *
     * @return A list of current train states.
     */
    List<TrainState> getTrains();
}
