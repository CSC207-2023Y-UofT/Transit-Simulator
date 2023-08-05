package interactor.train;

import interactor.station.StationState;
import model.train.TrainJob;

import java.util.Map;
import java.util.Optional;

/**
 * The TrainState dataclass represents the state of a train.
 */
public class TrainState {
    /**
     * The name of the train.
     */
    private final String name;
    /**
     * The capacity of the train.
     */
    private final int capacity;
    /**
     * The number of passengers on the train.
     */
    private final int occupation;
    /**
     * The staff on the train.
     */
    private final Map<TrainJob, Integer> staff;
    /**
     * The station the train is currently at.
     */
    private final Optional<StationState> currentStation;
    /**
     * The distance to the next node.
     */
    private final TrainNodeDistance nextNodeDistance;
    /**
     * The distance to the previous node.
     */
    private final TrainNodeDistance previousNodeDistance;

    /**
     * The constructor for the TrainState dataclass.
     *
     * @param name                 The name of the train.
     * @param capacity             The capacity of the train.
     * @param occupation           The number of passengers on the train.
     * @param staff                The staff on the train.
     * @param currentStation       The station the train is currently at.
     * @param nextNodeDistance     The distance to the next node.
     * @param previousNodeDistance The distance to the previous node.
     */
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

    /**
     * The getter for the name of the train.
     *
     * @return The name of the train.
     */
    public String getName() {
        return name;
    }

    /**
     * The getter for the capacity of the train.
     *
     * @return The capacity of the train.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * The getter for the number of passengers on the train.
     *
     * @return The number of passengers on the train.
     */
    public int getOccupation() {
        return occupation;
    }

    /**
     * The getter for the staff on the train.
     *
     * @return The staff on the train.
     */
    public Map<TrainJob, Integer> getStaff() {
        return staff;
    }

    /**
     * The getter for the station the train is currently at.
     *
     * @return The station the train is currently at.
     */
    public Optional<StationState> getCurrentStation() {
        return currentStation;
    }

    /**
     * The getter for the distance to the next node.
     *
     * @return The distance to the next node.
     */
    public Optional<TrainNodeDistance> getNextNodeDistance() {
        return Optional.ofNullable(nextNodeDistance);
    }

    /**
     * The getter for the distance to the previous node.
     *
     * @return The distance to the previous node.
     */
    public Optional<TrainNodeDistance> getPreviousNodeDistance() {
        return Optional.ofNullable(previousNodeDistance);
    }
}
