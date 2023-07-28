package model.train.track;

import model.node.Node;
import model.train.TrackRepo;

public class NodeTrackSegment extends TrackSegment {

    private final Node node;

    public NodeTrackSegment(TrackRepo repo, Node node, String id, int length) {
        super(repo, id, length);
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

}
