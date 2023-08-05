package interactor.train;

import interactor.station.StationState;
import model.train.TrainJob;

import java.util.Map;
import java.util.Optional;

public class TrainState {
    private final String name;
    private final int capacity;
    private final int occupation;
    private final Map<TrainJob, Integer> staff;
    private final Optional<StationState> currentStation;
    private final TrainNodeDistance nextNodeDistance;
    private final TrainNodeDistance previousNodeDistance;

    public TrainState(String name,
                      int capacity,
                      int occupation,
                      Map<TrainJob, Integer> staff,
                      StationState currentStation,
                      TrainNodeDistance nextNodeDistance,
                      TrainNodeDistance previousNodeDistance) {
        this.name = name;
        this.capacity = capacity;
        this.occupation = occupation;
        this.staff = staff;
        this.currentStation = Optional.ofNullable(currentStation);
        this.nextNodeDistance = nextNodeDistance;
        this.previousNodeDistance = previousNodeDistance;
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

    public Optional<StationState> getCurrentStation() {
        return currentStation;
    }

    public Optional<TrainNodeDistance> getNextNodeDistance() {
        return Optional.ofNullable(nextNodeDistance);
    }

    public Optional<TrainNodeDistance> getPreviousNodeDistance() {
        return Optional.ofNullable(previousNodeDistance);
    }
}
