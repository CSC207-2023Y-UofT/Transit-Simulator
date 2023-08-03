package model.control;

import model.Direction;
import model.train.*;
import model.train.track.TrackSegment;
import org.jetbrains.annotations.Nullable;
import model.node.Node;
import model.node.NodeFactory;
import model.node.NodeTracker;

import java.util.*;

/**
 * The TransitTracker class implements NodeTracker and TrainTracker interfaces.
 * It maintains lists of Train objects and Node objects, and provides functionality to create nodes and trains.
 */
public class TransitModel implements NodeTracker, TrainTracker {

    /** List of trains in the transit system */
    private final List<Train> trainList = new ArrayList<>();

    /** Map of nodes in the transit system, mapped by their identifiers */
    private final Map<String, Node> nodeMap = new HashMap<>();

    /** Repository of tracks in the transit system */
    private final TrackRepo trackRepo = new BasicTrackRepo();

    /**
     * Creates a train on the given track in the given direction with the given capacity.
     *
     * @param trackSegment The segment of the track where the train is to be created.
     * @param capacity The capacity of the train.
     * @return The created Train object.
     * @throws IllegalArgumentException if the track is not a valid track in this tracker's track repo.
     * @throws IllegalStateException if the track is occupied.
     */
    public Train createTrain(TrackSegment trackSegment, int capacity) {
        if (trackRepo.getTrack(trackSegment.getId()).orElse(null) != trackSegment) {
            throw new IllegalArgumentException("Track " + trackSegment.getId() + " created with wrong tracker");
        }

        TrainPosition position = TrainPosition.entryPoint(trackSegment, Direction.FORWARD);

        if (!trackSegment.isEmpty()) {
            throw new IllegalStateException("Track " + trackSegment.getId() + " is occupied");
        }

        Train train = new Train(this, position, capacity);
        trainList.add(train);

        return train;
    }

    /**
     * Returns an unmodifiable view of the map of nodes in the transit system.
     *
     * @return Map of Node objects, mapped by their identifiers.
     */
    @Override
    public Map<String, Node> getNodes() {
        return Collections.unmodifiableMap(nodeMap);
    }

    /**
     * Returns the node with the given name from the transit system.
     *
     * @param name The identifier of the node.
     * @return The Node object with the given name. Null if no such node exists.
     */
    @Override
    public @Nullable Node getNode(String name) {
        return nodeMap.get(name);
    }

    /**
     * Creates a node with the given identifier and length using the specified NodeFactory.
     *
     * @param nodeFactory The NodeFactory to create the node.
     * @param identifier The identifier of the node.
     * @return The created Node object.
     * @throws IllegalArgumentException if the node is not created with the correct tracker.
     */
    public Node createNode(NodeFactory nodeFactory, String identifier) {
        Node node = nodeFactory.createNode(this, identifier);
        if (node.getTracker() != this) {
            throw new IllegalArgumentException("Node " + identifier + " created with wrong tracker");
        }
        nodeMap.put(identifier, node);
        return node;
    }

    /**
     * Returns the track repository of the transit system.
     *
     * @return The TrackRepo object representing the track repository of the transit system.
     */
    @Override
    public TrackRepo getTrackRepo() {
        return trackRepo;
    }

    /**
     * Returns an unmodifiable view of the list of trains in the transit system.
     *
     * @return List of Train objects.
     */
    @Override
    public List<Train> getTrainList() {
        return trainList;
    }

}
