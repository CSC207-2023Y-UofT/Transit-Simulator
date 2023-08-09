package interactor.train;

import interactor.station.StationState;

/**
 * The TrainNodeDistance data class represents the distance between a station and a train.
 */
public class TrainNodeDistance {
    /**
     * The StationState.
     */
    private final StationState station;
    /**
     * The distance.
     */
    private final double distance;

    /**
     * Constructs a new TrainNodeDistance with the given station and distance.
     *
     * @param station  The StationState.
     * @param distance The distance.
     */
    public TrainNodeDistance(StationState station, double distance) {
        this.station = station;
        this.distance = distance;
    }

    /**
     * Gets the StationState.
     *
     * @return The StationState.
     */
    public StationState getStation() {
        return station;
    }

    /**
     * Gets the distance.
     *
     * @return The distance.
     */
    public double getDistance() {
        return distance;
    }
}
