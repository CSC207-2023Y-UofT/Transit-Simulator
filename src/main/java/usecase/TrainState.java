package usecase;

import model.train.TrainJob;

import java.util.Map;

public class TrainState {
    private final String name;
    private final int capacity;
    private final int occupation;
    private final Map<TrainJob, Integer> staff;
    private final String nextNode;

    public TrainState(String name, int capacity, int occupation, Map<TrainJob, Integer> staff, String nextNode) {
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

    public String getNextNode() {
        return nextNode;
    }
}
