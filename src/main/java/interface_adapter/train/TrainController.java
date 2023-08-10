package interface_adapter.train;

import app_business.boundary.ITrainInteractor;
import app_business.dto.TrainDTO;

import java.util.Optional;

/**
 * Controller, basically just a delegator to an ITrainInteractor instance
 */
public class TrainController {
    /**
     * The train interactor
     */
    private final ITrainInteractor interactor;

    /**
     * The constructor for the controller
     *
     * @param interactor The interactor
     */
    public TrainController(ITrainInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Finds a train by name
     *
     * @param name The name of the train to find
     * @return The train, or empty if the train was not found
     */
    public Optional<TrainDTO> find(String name) {
        return interactor.find(name);
    }
}
