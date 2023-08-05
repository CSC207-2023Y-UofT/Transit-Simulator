package interactor.train;

import employee.Employee;
import interactor.station.StationInteractor;
import interactor.station.StationState;
import model.Direction;
import model.control.TransitModel;
import model.train.Train;
import model.train.TrainRole;

import java.util.*;

public class TrainInteractor implements ITrainInteractor {
    private final TransitModel model;

    public TrainInteractor(TransitModel model) {
        this.model = model;
    }

    public TrainState getTrainState(String trainName) {
        return toState(model.getTrain(trainName));
    }

    public List<TrainState> getTrains() {
        List<TrainState> trains = new ArrayList<>();

        for (Train train : model.getTrainList()) {
            trains.add(toState(train));
        }

        return trains;
    }

    public static TrainState toState(Train train) {

        String name = train.getName();
        int capacity = train.getCapacity();
        int occupation = train.getPassengerList().size();

        Map<TrainRole, Integer> staff = new HashMap<>();
        for (Map.Entry<TrainRole, Employee> entry : train.getStaff().entrySet()) {
            staff.put(entry.getKey(), entry.getValue().getStaffNumber());
        }

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
                staff,
                currentStation.orElse(null),
                nextNodeDistance,
                previousNodeDistance);
    }
}
