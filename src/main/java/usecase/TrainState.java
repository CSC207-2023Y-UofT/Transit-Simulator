package usecase;

import model.train.TrainJob;

import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

public class TrainState {
    private final String name;
    private final int capacity;
    private final int occupation;
    private final Map<TrainJob, Integer> staff;
    private final Optional<String> nextNode;

    public TrainState(String name, int capacity, int occupation, Map<TrainJob, Integer> staff, Optional<String> nextNode) {
        this.name = name;
        this.capacity = capacity;
        this.occupation = occupation;
        this.staff = staff;
        this.nextNode = nextNode;
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
        return nextNode;
    }
}
