package app_business.train;

import app_business.station.StationDTO;

import java.util.Optional;

/**
 * This is a data class representing the state of a train.
 */
public class TrainDTO {

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
    private final StationDTO currentStation;

    /**
     * The distance to the next node.
     */
    private final TrainArrivalDTO nextNodeDistance;

    /**
     * The distance to the previous node.
     */
    private final TrainArrivalDTO previousNodeDistance;

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
    public TrainDTO(String name,
                    int capacity,
                    int occupation,
                    StationDTO currentStation,
                    TrainArrivalDTO nextNodeDistance,
                    TrainArrivalDTO previousNodeDistance) {
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
    public Optional<StationDTO> getCurrentStation() {
        return Optional.ofNullable(currentStation);
    }

    /**
     * The getter for the distance to the next node.
     *
     * @return The distance to the next node.
     */
    public Optional<TrainArrivalDTO> getNextNodeDistance() {
        return Optional.ofNullable(nextNodeDistance);
    }

    /**
     * The getter for the distance to the previous node.
     *
     * @return The distance to the previous node.
     */
    public Optional<TrainArrivalDTO> getPreviousNodeDistance() {
        return Optional.ofNullable(previousNodeDistance);
    }
}
