package interactor.train;

import employee.Employee;
import interactor.station.StationInteractor;
import interactor.station.StationState;
import model.Direction;
import model.control.TransitModel;
import model.node.Node;
import model.train.Train;
import model.train.TrainRole;

import java.util.*;

/**
 * The interactor for the train.
 */
public class TrainInteractor implements ITrainInteractor {
    /**
     * The transit model.
     */
    private final TransitModel model;

    /**
     * Constructs a new TrainInteractor with the given transit model.
     * @param model The transit model.
     */
    public TrainInteractor(TransitModel model) {
        this.model = model;
    }

    /**
     * Gets the train state for the given train name.
     * @param trainName The train name.
     * @return The train state.
     */
    public TrainState getTrainState(String trainName) {
        return toState(model.getTrain(trainName));
    }

    /**
     * Gets the train states for all trains.
     * @return The train states.
     */
    public List<TrainState> getTrains() {
        List<TrainState> trains = new ArrayList<>();

        for (Train train : model.getTrainList()) {
            trains.add(toState(train));
        }

        return trains;
    }

    /**
     * Returns the train state for the given train.
     * @param train The train.
     * @return the TrainState.
     */
    public static TrainState toState(Train train) {

        String name = train.getName();
        int capacity = train.getCapacity();
        int occupation = train.getPassengerList().size();


        // Forwards
        Optional<StationState> nextNode = train.getNextNode(Direction.FORWARD)
                .map(StationInteractor::toState);

        Optional<Double> distanceToNextNode = train.getDistanceToNextNode(Direction.FORWARD);

        // Backwards
        Optional<StationState> previousNode = train.getNextNode(Direction.BACKWARD)
                .map(StationInteractor::toState);

        Optional<Double> distanceToPreviousNode = train.getDistanceToNextNode(Direction.BACKWARD);

        TrainNodeDistance nextNodeDistance = null;
        TrainNodeDistance previousNodeDistance = null;

        if (nextNode.isPresent()) {
            nextNodeDistance = new TrainNodeDistance(nextNode.get(), distanceToNextNode.orElseThrow());
        }

        if (previousNode.isPresent()) {
            previousNodeDistance = new TrainNodeDistance(previousNode.get(), distanceToPreviousNode.orElseThrow());
        }

        Optional<StationState> currentStation = train
                .getPosition()
                .getTrack()
                .getNode()
                .map(StationInteractor::toState);

        return new TrainState(name,
                capacity,
                occupation,
                currentStation.orElse(null),
                nextNodeDistance,
                previousNodeDistance);
    }
}
