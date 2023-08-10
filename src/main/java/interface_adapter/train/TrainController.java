package interface_adapter.train;

import app_business.boundary.ITrainInteractor;
import app_business.dto.TrainDTO;

import java.util.Optional;

public class TrainController {
    private final ITrainInteractor interactor;

    public TrainController(ITrainInteractor interactor) {
        this.interactor = interactor;
    }

    public Optional<TrainDTO> find(String name) {
        return interactor.find(name);
    }
}