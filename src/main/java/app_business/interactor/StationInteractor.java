package app_business.interactor;

import app_business.boundary.IStationInteractor;
import app_business.dto.StationDTO;
import entity.model.Direction;
import entity.model.control.TransitModel;
import entity.model.node.Node;
import entity.model.node.line.NodeLineProfile;
import entity.model.node.line.TrainArrival;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The interactor for the station.
 */
public class StationInteractor implements IStationInteractor {

    /**
     * The transit model.
     */
    private final TransitModel model;

    /**
     * Constructs a new StationInteractor with the given transit model.
     *
     * @param model The transit model.
     */
    public StationInteractor(TransitModel model) {
        this.model = model;
    }

    /**
     * Gets the station state for the given station name.
     *
     * @param stationName The station name.
     * @return The station state.
     */
    public Optional<StationDTO> find(String stationName) {
        return model.getNode(stationName)
                .map(StationInteractor::toDTO);
    }

    /**
     * Gets the next station given the line number, station name and direction.
     *
     * @param line        The line number.
     * @param stationName The station name.
     * @param direction   The direction.
     * @return The next station state, if any.
     */
    public Optional<StationDTO> getNextStation(String stationName, int line, Direction direction) {
        return model.getNode(stationName)
                .flatMap(n -> n.getLineProfile(line))
                .flatMap(p -> p.getNextNode(direction))
                .map(StationInteractor::toDTO);
    }

    /**
     * Gets a list of stations in the transit model.
     *
     * @return The list of stations.
     */
    public List<StationDTO> getStations() {
        return model.getNodes().values()
                .stream()
                .map(StationInteractor::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Gets the time till the next arrival given the station name, line number and direction.
     *
     * @param stationName The station name.
     * @param line        The line number.
     * @param direction   The direction.
     * @return The time till the next arrival, if any. In milliseconds
     */
    public Optional<Long> getTimeTillNextArrival(String stationName, int line, Direction direction) {
        return model.getNode(stationName)
                .flatMap(n -> n.getLineProfile(line))
                .map(p -> p.nextArrivals(direction, 1))
                .flatMap(a -> a.stream().findFirst())
                .map(TrainArrival::getDelay);
    }

    /**
     * Converts a node to a station state.
     *
     * @param node The node.
     * @return The station state.
     */
    public static StationDTO toDTO(Node node) {

        List<Integer> lineProfiles = node.getLineProfiles()
                .stream()
                .map(NodeLineProfile::getLineNumber)
                .collect(Collectors.toList());

        return new StationDTO(
                node.getName(),
                lineProfiles,
                node.getX(),
                node.getY()
        );
    }
}
