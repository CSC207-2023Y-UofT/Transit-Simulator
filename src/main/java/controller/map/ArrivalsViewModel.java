package controller.map;

import interactor.station.IStationInteractor;
import interactor.station.StationDTO;
import model.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The ArrivalsViewModel class is a view model class for showing a station's
 * next arrival time.
 */
public class ArrivalsViewModel {
    private final IStationInteractor stationInteractor;
    private Map<Integer, Map<String, Long>> nextArrivals = new HashMap<>();
    private final StationDTO station;

    /**
     * Constructs a new ArrivalsViewModel with the given station and station interactor.
     * @param station The station to show the next arrivals for.
     * @param stationInteractor The station interactor.
     */
    public ArrivalsViewModel(StationDTO station, IStationInteractor stationInteractor) {
        this.station = station;
        this.stationInteractor = stationInteractor;
    }

    /**
     * Updates the next arrivals for the station.
     */
    public void update() {

        nextArrivals.clear();

        // For each line the station is part of
        for (int lineNumber : station.getLines()) {
            // Get the next arrivals for the station
            Map<String, Long> arrivals = new HashMap<>();

            // Get the next arrivals in both directions
            var forward = stationInteractor.getTimeTillNextArrival(station.getName(),
                    lineNumber,
                    Direction.FORWARD);
            var backward = stationInteractor.getTimeTillNextArrival(station.getName(),
                    lineNumber,
                    Direction.BACKWARD);

            var forwardStation = stationInteractor.getNextStation(station.getName(),
                    lineNumber,
                    Direction.FORWARD);

            var backwardStation = stationInteractor.getNextStation(station.getName(),
                    lineNumber,
                    Direction.BACKWARD);

            // Add to temp inner map
            forwardStation.ifPresent(next -> forward.ifPresent(time -> arrivals.put(next.getName(), time)));
            backwardStation.ifPresent(prev -> backward.ifPresent(time -> arrivals.put(prev.getName(), time)));

            // Add the next arrivals to the map
            nextArrivals.put(lineNumber, arrivals);
        }
    }

    public Map<Integer, Map<String, Long>> getNextArrivals() {
        return nextArrivals;
    }

    public StationDTO getStation() {
        return station;
    }
}
