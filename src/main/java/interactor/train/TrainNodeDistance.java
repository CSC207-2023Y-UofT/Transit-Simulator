package interactor.train;

import interactor.station.StationState;

public class TrainNodeDistance {
    private final StationState station;
    private final double distance;

    public TrainNodeDistance(StationState station, double distance) {
        this.station = station;
        this.distance = distance;
    }

    public StationState getStation() {
        return station;
    }

    public double getDistance() {
        return distance;
    }
}
