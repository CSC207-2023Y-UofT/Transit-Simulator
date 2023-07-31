package model.train.track;

import model.node.Node;
import model.train.TrackRepo;

/**
 * The NodeTrackSegment class represents a track segment associated with a specific node in a track system.
 * It extends the TrackSegment class and inherits its properties and functionality.
 * NodeTrackSegments are used to represent the track segments that nodes hold.
 */
public class NodeTrackSegment extends TrackSegment {

    private final Node node;

    /**
     * Constructs a new NodeTrackSegment object with the specified track repository, node, ID, and length.
     *
     * @param repo The TrackRepo instance to associate with the track segment.
     * @param node The Node object representing the node associated with this track segment.
     * @param id The unique identifier for the track segment.
     * @param length The length of the track segment in meters.
     */
    public NodeTrackSegment(TrackRepo repo, Node node, String id, int length) {
        super(repo, id, length);
        this.node = node;
    }

    /**
     * Retrieves the Node associated with this track segment.
     *
     * @return The Node object representing the node associated with this track segment.
     */
    public Node getNode() {
        return node;
    }

}
