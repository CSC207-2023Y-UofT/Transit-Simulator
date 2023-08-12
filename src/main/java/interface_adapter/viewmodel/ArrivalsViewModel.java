package interface_adapter.viewmodel;

import app_business.boundary.IStationInteractor;
import app_business.dto.StationDTO;
import entity.model.Direction;

import java.util.HashMap;
import java.util.Map;

/**
 * The ArrivalsViewModel class is a view model class for showing a station's
 * next arrival time. It facilitates updating and fetching the next arrival times for
 * each direction at a given station.
 */
public class ArrivalsViewModel {

    /**
     * Interactor to communicate with the underlying station data.
     */
    private final IStationInteractor stationInteractor;

    /**
     * A map storing the next arrival times for each line and direction.
     * The outer key is the line number and the inner key is the direction.
     */
    private final Map<Integer, Map<String, Long>> nextArrivals = new HashMap<>();

    /**
     * The station for which the view model provides the next arrival data.
     */
    private final StationDTO station;

    /**
     * Constructs a new ArrivalsViewModel with the given station and station interactor.
     *
     * @param station The station to show the next arrivals for.
     * @param stationInteractor The station interactor to fetch next arrival data.
     */
    public ArrivalsViewModel(StationDTO station, IStationInteractor stationInteractor) {
        this.station = station;
        this.stationInteractor = stationInteractor;
    }

    /**
     * Updates the next arrivals for the station by querying the station interactor.
     * The data is stored in the nextArrivals map.
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

    /**
     * Returns the map containing the next arrival times for each line and direction.
     *
     * @return The map of next arrivals.
     */
    public Map<Integer, Map<String, Long>> getNextArrivals() {
        return nextArrivals;
    }

    /**
     * Returns the station associated with this view model.
     *
     * @return The StationDTO object representing the station.
     */
    public StationDTO getStation() {
        return station;
    }
}
