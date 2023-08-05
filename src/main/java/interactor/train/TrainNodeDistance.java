package interactor.train;

import interactor.station.StationState;

/**
 * Data class to represent a train's distance from a station.
 */
public class TrainNodeDistance {
    /**
     * The station
     */
    private final StationState station;
    /**
     * The distance to the station
     */
    private final double distance;

    /**
     * Create a new TrainNodeDistance object.
     *
     * @param station  The station
     * @param distance The distance to the station
     */
    public TrainNodeDistance(StationState station, double distance) {
        this.station = station;
        this.distance = distance;
    }

    /**
     * Get the station
     * @return The station
     */
    public StationState getStation() {
        return station;
    }

    /**
     * Get the distance to the station
     * @return The distance to the station
     */
    public double getDistance() {
        return distance;
    }
}
