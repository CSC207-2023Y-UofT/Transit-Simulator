package app_business.interactor;

import app_business.dto.TrainArrivalDTO;
import app_business.dto.TrainDTO;
import app_business.dto.StationDTO;
import app_business.boundary.ITrainInteractor;
import entity.model.Direction;
import entity.model.control.TransitModel;
import entity.model.train.Train;
import entity.model.train.TrainStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The interactor for the train.
 */
public class TrainInteractor implements ITrainInteractor {
    /**
     *
     * The transit model.
     */
    private final TransitModel model;

    /**
     * Constructs a new TrainInteractor with the given transit model.
     *
     * @param model The transit model.
     */
    public TrainInteractor(TransitModel model) {
        this.model = model;
    }

    /**
     * Gets the train state for the given train name.
     *
     * @param trainName The train name.
     * @return The train state.
     */
    public Optional<TrainDTO> find(String trainName) {
        Train train = model.getTrain(trainName);
        if (train == null) return Optional.empty();
        return Optional.of(toDTO(train));
    }

    /**
     * Gets the train states for all trains.
     *
     * @return The train states.
     */
    public List<TrainDTO> getTrains() {
        List<TrainDTO> trains = new ArrayList<>();

        for (Train train : model.getTrainList()) {
            trains.add(toDTO(train));
        }

        return trains;
    }

    @Override
    public void setNeedsMaintenance(String trainName, boolean needsMaintenance) {
        Train train = model.getTrain(trainName);
        if (train == null) return;
        if (needsMaintenance) {
            train.setStatus(TrainStatus.UNDER_MAINTENANCE);
        } else {
            train.setStatus(TrainStatus.IN_SERVICE);
        }
    }

    /**
     * Returns the train state for the given train.
     *
     * @param train The train.
     * @return the TrainState.
     */
    public static TrainDTO toDTO(Train train) {

        String name = train.getName();
        int capacity = train.getCapacity();
        int occupation = train.getPassengerList().size();


        // Forwards
        Optional<StationDTO> nextNode = train.getNextNode(Direction.FORWARD)
                .map(StationInteractor::toDTO);

        Optional<Double> distanceToNextNode = train.getDistanceToNextNode(Direction.FORWARD);

        // Backwards
        Optional<StationDTO> previousNode = train.getNextNode(Direction.BACKWARD)
                .map(StationInteractor::toDTO);

        Optional<Double> distanceToPreviousNode = train.getDistanceToNextNode(Direction.BACKWARD);

        TrainArrivalDTO nextNodeDistance = null;
        TrainArrivalDTO previousNodeDistance = null;

        if (nextNode.isPresent()) {
            nextNodeDistance = new TrainArrivalDTO(nextNode.get(), distanceToNextNode.orElseThrow());
        }

        if (previousNode.isPresent()) {
            previousNodeDistance = new TrainArrivalDTO(previousNode.get(), distanceToPreviousNode.orElseThrow());
        }

        Optional<StationDTO> currentStation = train
                .getPosition()
                .getTrack()
                .getNode()
                .map(StationInteractor::toDTO);

        return new TrainDTO(name,
                capacity,
                occupation,
                currentStation.orElse(null),
                nextNodeDistance,
                previousNodeDistance,
                train.getStatus());
    }
}
