package interactor.train;

import interactor.station.StationState;

import java.util.Optional;

/**
 * This is a data class representing the state of a train.
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
     * The station the train is currently at.
     */
    private final StationState currentStation;
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
     * @param currentStation       The station the train is currently at.
     * @param nextNodeDistance     The distance to the next node.
     * @param previousNodeDistance The distance to the previous node.
     */
    public TrainState(String name,
                      int capacity,
                      int occupation,
                      StationState currentStation,
                      TrainNodeDistance nextNodeDistance,
                      TrainNodeDistance previousNodeDistance) {
        this.name = name;
        this.capacity = capacity;
        this.occupation = occupation;
        this.currentStation = currentStation;
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
     * The getter for the station the train is currently at.
     *
     * @return The station the train is currently at.
     */
    public Optional<StationState> getCurrentStation() {
        return Optional.ofNullable(currentStation);
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
