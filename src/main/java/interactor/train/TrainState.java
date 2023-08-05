package interactor.train;

import model.train.TrainJob;

import java.util.Map;
import java.util.Optional;

public class TrainState {
    private final String name;
    private final int capacity;
    private final int occupation;
    private final Map<TrainJob, Integer> staff;
    private final String nextNode;
    private final Double distanceToNextNode;

    public TrainState(String name,
                      int capacity,
                      int occupation,
                      Map<TrainJob, Integer> staff,
                      String nextNode,
                      Double distanceToNextNode) {
        this.name = name;
        this.capacity = capacity;
        this.occupation = occupation;
        this.staff = staff;
        this.nextNode = nextNode;
        this.distanceToNextNode = distanceToNextNode;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupation() {
        return occupation;
    }

    public Map<TrainJob, Integer> getStaff() {
        return staff;
    }

    public Optional<String> getNextNode() {
        return Optional.ofNullable(nextNode);
    }
    public Optional<Double> getDistanceToNextNode() {
        return Optional.ofNullable(distanceToNextNode);
    }
}
