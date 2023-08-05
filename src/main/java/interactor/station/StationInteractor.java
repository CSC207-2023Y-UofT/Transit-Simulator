package interactor.station;

import model.Direction;
import model.control.TransitModel;
import model.node.Node;
import model.node.NodeLineProfile;
import model.node.TrainArrival;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StationInteractor {

    private final TransitModel model;

    public StationInteractor(TransitModel model) {
        this.model = model;
    }

    public Optional<StationState> getStationState(String stationName) {
        Node node = model.getNode(stationName);

        if (node == null) {
            return Optional.empty();
        }

        return Optional.of(toState(node));
    }

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

    public List<StationState> getStations() {
        List<StationState> stations = new ArrayList<>();

        for (Node node : model.getNodes().values()) {
            stations.add(toState(node));
        }

        return stations;
    }

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
