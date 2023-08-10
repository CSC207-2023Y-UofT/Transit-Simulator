package interface_adapter.controller;

import app_business.boundary.ITrainInteractor;
import app_business.dto.TrainDTO;

import java.util.List;
import java.util.Optional;

public class TrainController {
    private final ITrainInteractor interactor;

    public TrainController(ITrainInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Find a train by name.
     */
    public Optional<TrainDTO> find(String name) {
        return interactor.find(name);
    }

    /**
     * Get all trains.
     */
    public List<TrainDTO> findAll() {
        return interactor.getTrains();
    }
}
