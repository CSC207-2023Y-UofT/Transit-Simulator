package interface_adapter.train;

import app_business.boundary.ITrainInteractor;
import app_business.dto.TrainDTO;

import java.util.Optional;

/**
 * The TrainController class represents the controller for the train entity.
 */
public class TrainController {

    /**
     * The interactor for the train entity.
     */
    private final ITrainInteractor interactor;

    /**
     * Constructs a new TrainController with the given interactor.
     *
     * @param interactor The interactor for the train entity.
     */
    public TrainController(ITrainInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Finds a train by its name.
     * @param name The name of the train.
     * @return An optional containing the train if it exists, an empty optional otherwise.
     */
    public Optional<TrainDTO> find(String name) {
        return interactor.find(name);
    }
}
