package interactor.station;

import model.Direction;

import java.util.List;
import java.util.Optional;

public interface StationInputBoundary {
    Optional<StationState> getStationState(String stationName);
    Optional<StationState> getNextStation(int line, String stationName, Direction direction);
    Optional<Long> getTimeTillNextArrival(String stationName, int line, Direction direction);
    List<StationState> getStations();
}
