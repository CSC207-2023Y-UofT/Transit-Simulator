package interactor.station;

import model.Direction;

import java.util.List;
import java.util.Optional;

/**
 * This interface defines the input boundary for the station interactor.
 */
public interface IStationInteractor {

    /**
     * Returns the state of the station with the given name.
     */
    Optional<StationDTO> getStation(String stationName);

    /**
     * Returns the state of the next station in the given direction.
     *
     * @param line        The line number you are looking for data on.
     * @param stationName The name of the station you are looking for data on.
     * @param direction   The direction on the line specified that you want to look in.
     * @return The state of the next station in the given direction.
     */
    Optional<StationDTO> getNextStation(int line, String stationName, Direction direction);

    /**
     * Returns the time until the next arrival at the given station.
     *
     * @param stationName The name of the station you are looking for data on.
     * @param line        The line number you are looking for data on.
     * @param direction   The direction on the line specified that you want to look in.
     * @return The time until the next arrival at the given station.
     */
    Optional<Long> getTimeTillNextArrival(String stationName, int line, Direction direction);

    /**
     * Returns a list of all stations.
     *
     * @return A list of all stations.
     */
    List<StationDTO> getStations();
}
