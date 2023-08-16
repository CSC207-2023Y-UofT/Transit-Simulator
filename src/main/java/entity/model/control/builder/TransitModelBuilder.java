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

public class TransitModelBuilder {
    private final TransitModel model = new TransitModel();
    private final Set<Integer> createdLines = new HashSet<>();

    public TransitModelBuilder station(String name, int x, int y) {
        Preconditions.checkArgument(model.getNode(name).isEmpty(), "Station already created");
        Node node = model.createNode(new StationFactory(), name);
        node.setX(x);
        node.setY(y);
        return this;
    }

    public TransitModelBuilder line(int lineNumber, String... stations) {
        return line(lineNumber, List.of(stations), false);
    }

    public TransitModelBuilder cyclicLine(int lineNumber, String... stations) {
        return line(lineNumber, List.of(stations), true);
    }

    public TransitModelBuilder line(int lineNumber, List<String> stations) {
        return line(lineNumber, stations, false);
    }

    public TransitModelBuilder cyclicLine(int lineNumber, List<String> stations) {
        return line(lineNumber, stations, true);
    }

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
        }

        if (cyclic) {
            var first = nodes.get(0);
            var last = nodes.get(nodes.size() - 1);
            if (first != last) linkStations(last, first, lineNumber);
        }

        return this;
    }

    private void linkStations(Node station1, Node station2, int lineNumber) {
        var profile1 = station1.createLineProfile(lineNumber);
        var profile2 = station2.createLineProfile(lineNumber);

        var n1For = profile1.getTrack(Direction.FORWARD);
        var n1Back = profile2.getTrack(Direction.BACKWARD);

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

        n2For.linkForward(interBack);
        n2Back.linkBackward(interFor);
    }

    public TransitModel build() {
        return model;
    }
}
