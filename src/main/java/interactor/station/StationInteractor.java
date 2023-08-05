package interactor.station;

import model.Direction;
import model.control.TransitModel;
import model.node.Node;
import model.node.NodeLineProfile;
import model.node.TrainArrival;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The interactor for the station.
 */
public class StationInteractor {
    /**
     * The transit model.
     */
    private final TransitModel model;

    /**
     * Constructs a new StationInteractor with the given transit model.
     * @param model The transit model.
     */
    public StationInteractor(TransitModel model) {
        this.model = model;
    }

    /**
     * Gets the station state for the given station name.
     * @param stationName The station name.
     * @return The station state.
     */
    public Optional<StationState> getStationState(String stationName) {
        Node node = model.getNode(stationName);
        if (node == null) {
            return Optional.empty();
        }

        return Optional.of(toState(node));
    }

    /**
     * Gets the next station given the line number, station name and direction.
     * @param line The line number.
     * @param stationName The station name.
     * @param direction The direction.
     * @return The next station state, if any.
     */
    public Optional<StationState> getNextStation(int line, String stationName, Direction direction) {
        Node node = model.getNode(stationName);

        if (node == null) {
            return Optional.empty();
        }

        NodeLineProfile lineProfile = node.getLineProfile(line).orElseThrow();
        Node nextNode = lineProfile.getNextNode(direction).orElse(null);

        if (nextNode == null) {
            return Optional.empty();
        }

        return getStationState(nextNode.getName());
    }

    /**
     * Gets a list of stations in the transit model.
     * @return The list of stations.
     */
    public List<StationState> getStations() {
        List<StationState> stations = new ArrayList<>();

        for (Node node : model.getNodes().values()) {
            stations.add(toState(node));
        }

        return stations;
    }

    /**
     * Gets the time till the next arrival given the station name, line number and direction.
     * @param stationName The station name.
     * @param line The line number.
     * @param direction The direction.
     * @return The time till the next arrival, if any. In Epoch Time format.
     */
    public Optional<Long> getTimeTillNextArrival(String stationName, int line, Direction direction) {
        Node node = model.getNode(stationName);

        if (node == null) {
            return Optional.empty();
        }

        NodeLineProfile lineProfile = node.getLineProfile(line).orElseThrow();
        List<TrainArrival> arrivals = lineProfile.nextArrivals(direction, 1);


        if (arrivals.isEmpty()) {
            return Optional.empty();
        }

        TrainArrival arrival = arrivals.get(0);

        return Optional.of(arrival.getDelay());
    }

    /**
     * Converts a node to a station state.
     * @param node The node.
     * @return The station state.
     */
    public static StationState toState(Node node) {
        List<Integer> lineProfiles = new ArrayList<>();
        for (NodeLineProfile profile : node.getLineProfiles()) {
            lineProfiles.add(profile.getLineNumber());
        }

        return new StationState(
                node.getName(),
                lineProfiles,
                node.getX(),
                node.getY()
        );
    }
}
