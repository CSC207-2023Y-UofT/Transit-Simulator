package interactor.train;

import employee.Employee;
import model.Direction;
import model.control.TransitModel;
import model.node.Node;
import model.train.Train;
import model.train.TrainJob;

import java.util.*;

public class TrainInteractor {
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

    private TrainState toState(Train train) {

        String name = train.getName();
        int capacity = train.getCapacity();
        int occupation = train.getPassengerList().size();

        Map<TrainJob, Integer> staff = new HashMap<>();
        for (Map.Entry<TrainJob, Employee> entry : train.getStaff().entrySet()) {
            staff.put(entry.getKey(), entry.getValue().getStaffNumber());
        }

        Optional<String> nextNode = train.getNextNode(Direction.FORWARD)
                .map(Node::getName);
        Optional<Double> distanceToNextNode = train.getDistanceToNextNode(Direction.FORWARD);

        return new TrainState(name,
                capacity,
                occupation,
                staff,
                nextNode.orElse(null),
                distanceToNextNode.orElse(null));
    }
}
