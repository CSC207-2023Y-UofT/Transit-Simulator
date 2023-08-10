package controller.train;

import interactor.train.ITrainInteractor;
import interactor.train.TrainDTO;

import java.util.Optional;

public class TrainController {
    private final ITrainInteractor interactor;

    public TrainController(ITrainInteractor interactor) {
        this.interactor = interactor;
    }

    public Optional<TrainDTO> getTrain(String name) {
        return interactor.getTrain(name);
    }
}
