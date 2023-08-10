package interface_adapter.train;

import app_business.train.ITrainInteractor;
import app_business.train.TrainDTO;

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
