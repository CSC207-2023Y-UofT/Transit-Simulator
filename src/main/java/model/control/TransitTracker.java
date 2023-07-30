package model.control;

import model.Direction;
import model.train.*;
import model.train.track.TrackSegment;
import org.jetbrains.annotations.Nullable;
import model.node.Node;
import model.node.NodeFactory;
import model.node.NodeTracker;

import javax.sound.midi.Track;
import java.util.*;

// TODO separate this out into different places
public class TransitTracker implements NodeTracker, TrainTracker {

    private final List<Train> trainList = new ArrayList<>();
    private final Map<String, Node> nodeMap = new HashMap<>();

    private final TrackRepo trackRepo = new BasicTrackRepo();

    /**
     * Creates a train on the given track in the given direction with the given capacity.
     * @throws IllegalArgumentException if the track is not a valid track in this tracker's track repo.
     * @throws IllegalStateException if the track is occupied.
     */
    public Train createTrain(TrackSegment trackSegment, Direction direction, int capacity) {
        if (trackRepo.getTrack(trackSegment.getId()).orElse(null) != trackSegment) {
            throw new IllegalArgumentException("Track " + trackSegment.getId() + " created with wrong tracker");
        }

        TrainPosition position = TrainPosition.entryPoint(trackSegment, direction);

        if (!trackSegment.isEmpty()) {
            throw new IllegalStateException("Track " + trackSegment.getId() + " is occupied");
        }

        Train train = new Train(this, direction, position, capacity);
        trainList.add(train);

        return train;
    }

    @Override
    public List<Train> getTrains() {
        return trainList;
    }

    @Override
    public Map<String, Node> getNodes() {
        return Collections.unmodifiableMap(nodeMap);
    }

    @Override
    public @Nullable Node getNode(String name) {
        return nodeMap.get(name);
    }

    public Node createNode(NodeFactory nodeFactory, String identifier, double length) {
        Node node = nodeFactory.createNode(this, identifier, length);
        if (node.getTracker() != this) {
            throw new IllegalArgumentException("Node " + identifier + " created with wrong tracker");
        }
        nodeMap.put(identifier, node);
        return node;
    }

    @Override
    public TrackRepo getTrackRepo() {
        return trackRepo;
    }

}
