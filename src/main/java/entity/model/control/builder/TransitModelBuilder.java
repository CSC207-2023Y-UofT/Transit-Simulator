package entity.model.control.builder;

import entity.model.Direction;
import entity.model.control.TransitModel;
import entity.model.node.Node;
import entity.model.node.station.StationFactory;
import entity.model.train.track.TrackSegment;
import util.Preconditions;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * || BUILDER PATTERN USED ||
 * Builder for a transit model
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public class TransitModelBuilder {
    /**
     * The model that is being built
     */
    private final TransitModel model = new TransitModel();

    /**
     * The set of created lines
     */
    private final Set<Integer> createdLines = new HashSet<>();

    /**
     * Creates a new station with the specified name and coordinates
     *
     * @param name The name of the station
     * @param x    The x coordinate
     * @param y    The y coordinate
     */
    public TransitModelBuilder station(String name, int x, int y) {
        Preconditions.checkArgument(model.getNode(name).isEmpty(), "Station already created");
        Node node = model.createNode(new StationFactory(), name);
        node.setX(x);
        node.setY(y);
        return this;
    }

    /**
     * Creates a line with the specified line number and specified
     *
     * @param lineNumber The line number
     * @param stations   The list of stations
     */
    public TransitModelBuilder line(int lineNumber, String... stations) {
        return line(lineNumber, List.of(stations), false);
    }

    /**
     * Creates a cyclic line with the specified line number and specified
     *
     * @param lineNumber The line number
     * @param stations   The list of stations
     * @return The builder
     */
    public TransitModelBuilder cyclicLine(int lineNumber, String... stations) {
        return line(lineNumber, List.of(stations), true);
    }

    /**
     * Creates a line with the specified line number and specified
     *
     * @param lineNumber The line number
     * @param stations   The list of stations
     * @return The builder
     */
    public TransitModelBuilder line(int lineNumber, List<String> stations) {
        return line(lineNumber, stations, false);
    }

    /**
     * Creates a cyclic line with the specified line number and specified
     * list of stations
     *
     * @param lineNumber The line number
     * @param stations   The list of stations
     */
    public TransitModelBuilder cyclicLine(int lineNumber, List<String> stations) {
        return line(lineNumber, stations, true);
    }

    /**
     * Creates a line with the specified line number and specified
     * list of stations
     *
     * @param lineNumber The line number
     * @param stations   The list of stations
     * @param cyclic     Whether the line is cyclic
     */
    private TransitModelBuilder line(int lineNumber, List<String> stations, boolean cyclic) {
        Preconditions.checkArgument(!createdLines.contains(lineNumber), "Line already created");
        Preconditions.checkArgument(stations.size() > 1, "At least two stations are required");
        stations.forEach(n -> Preconditions.checkArgument(model.getNode(n).isPresent(), "Station does not exist"));

        createdLines.add(lineNumber);

        var nodes = stations.stream()
                .map(model::getNode)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        var curr = nodes.get(0);

        for (int i = 1; i < nodes.size(); i++) {
            var next = nodes.get(i);
            linkStations(curr, next, lineNumber);
            curr = next;
        }

        var first = nodes.get(0);
        var last = nodes.get(nodes.size() - 1);
        if (first != last) {
            if (cyclic) {
                linkStations(last, first, lineNumber);
            } else {
                // For the endpoints, link the forward and backward tracks
                var prof = first.createLineProfile(lineNumber);
                var n1For = prof.getTrack(Direction.FORWARD);
                var n1Back = prof.getTrack(Direction.BACKWARD);

                n1For.linkBackward(n1Back);

                var prof2 = last.createLineProfile(lineNumber);
                var n2For = prof2.getTrack(Direction.FORWARD);
                var n2Back = prof2.getTrack(Direction.BACKWARD);

                n2For.linkForward(n2Back);
            }
        }

        return this;
    }

    /**
     * Creates an edge between two stations.
     *
     * @param station1   The first station
     * @param station2   The second station
     * @param lineNumber The line number
     */
    private void linkStations(Node station1, Node station2, int lineNumber) {
        var profile1 = station1.createLineProfile(lineNumber);
        var profile2 = station2.createLineProfile(lineNumber);

        var n1For = profile1.getTrack(Direction.FORWARD);
        var n1Back = profile1.getTrack(Direction.BACKWARD);

        var n2For = profile2.getTrack(Direction.FORWARD);
        var n2Back = profile2.getTrack(Direction.BACKWARD);

        assert station1.getTracker().getTrackRepo() == station2.getTracker().getTrackRepo();
        var repo = station1.getTracker().getTrackRepo();

        var n1X = station1.getX();
        var n1Y = station1.getY();

        var n2X = station2.getX();
        var n2Y = station2.getY();

        var distance = Math.sqrt(Math.pow(n1X - n2X, 2) + Math.pow(n1Y - n2Y, 2));

        var interFor = new TrackSegment(repo,
                station1.getName() + "-" + station2.getName() + "-FOR-LINE-" + lineNumber,
                distance);
        var interBack = new TrackSegment(repo,
                station1.getName() + "-" + station2.getName() + "-BACK-LINE-" + lineNumber,
                distance);

        n1For.linkForward(interFor);
        n1Back.linkBackward(interBack);

        interFor.linkForward(n2For);
        interBack.linkBackward(n2Back);

        repo.addTrack(interFor);
        repo.addTrack(interBack);
    }

    public TransitModel build() {
        return model;
    }
}
