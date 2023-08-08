package model.node;

import model.Direction;
import model.train.TrackRepo;
import model.train.Train;
import model.train.track.NodeTrackSegment;
import model.train.track.TrackSegment;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Represents a line profile for a Node in the transit system.
 * This class is responsible for managing all the TrackSegments that are attached to a specific Node and Line.
 */
public class NodeLineProfile {

    /**
     * The Node object this profile is associated with.
     */
    private final Node node;

    /**
     * The line number for this profile.
     */
    private final int lineNumber;

    /**
     * Map of TrackSegments associated with this profile, keyed by direction.
     */
    private final Map<Direction, TrackSegment> tracks = new HashMap<>();

    /**
     * Constructs a NodeLineProfile for the given Node and line number.
     *
     * @param node       the Node object
     * @param lineNumber the line number
     */
    public NodeLineProfile(Node node, int lineNumber) {
        this.node = node;
        this.lineNumber = lineNumber;

        // Create the tracks in each direction
        TrackRepo trackRepo = node.getTracker().getTrackRepo();
        for (Direction value : Direction.values()) {
            tracks.put(
                    value,
                    new NodeTrackSegment(
                            trackRepo,
                            node,
                            node.getName() + "-" + lineNumber + "-" + value,
                            Train.LENGTH
                    )
            );
        }

        tracks.values().forEach(trackRepo::addTrack);  // Similar to lambda expressions
    }

    /**
     * Returns the next Node in the given direction, if it exists.
     *
     * @param direction the direction to check for the next Node
     * @return an Optional containing the next Node, or empty if there is no next Node
     */
    public Optional<Node> getNextNode(Direction direction) {
        List<TrackSegment> next = getTrack(direction).getNextTrackSegments();
        if (next.isEmpty()) return Optional.empty();

        for (TrackSegment segment : next) {
            if (segment.getNode().isPresent()) return segment.getNode();
        }

        return Optional.empty();
    }

    /**
     * Returns the line number for this profile.
     *
     * @return an int representing the line number
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Returns the Node this profile is associated with.
     *
     * @return the Node object
     */
    public @NotNull Node getNode() {
        return node;
    }

    /**
     * Returns the TrackSegment that corresponds to a given direction.
     *
     * @param direction the direction of the track
     * @return the corresponding TrackSegment
     */
    public @NotNull TrackSegment getTrack(Direction direction) {
        return tracks.get(direction);
    }

    /**
     * Returns a list of TrainArrival objects for the next arriving trains in the given direction.
     *
     * @param direction the direction to check for arriving trains
     * @param numTrains the number of arriving trains to return
     * @return a List of TrainArrival objects
     */
    public List<TrainArrival> nextArrivals(Direction direction, int numTrains) {
        List<TrackSegment> trackSegments = getTrack(direction)
                .getNextTrackSegments(Direction.BACKWARD);

        List<TrainArrival> arrivals = new ArrayList<>();

        double waitTime = 0;


        for (TrackSegment trackSegment : trackSegments) {
            if (trackSegment.getTrain() == null) continue;
            if (arrivals.size() >= numTrains) break;

            Train train = trackSegment.getTrain();

            // Calculate the added wait time
            if (trackSegment.getNode().isPresent()) {
                // The train will probably stop here, so add the extra
                // time that will be spent at the node/station
                waitTime += Train.STATION_WAIT_TIME;
            }

            waitTime += trackSegment.getLength() / Train.MAX_SPEED;

            TrainArrival arrival = new TrainArrival(train, node, (long) waitTime);
            arrivals.add(arrival);
        }

        return arrivals;
    }

}
