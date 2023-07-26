package train;

import org.jetbrains.annotations.NotNull;
import train.Node;
import train.NodeTracker;

public class TrackSegment extends Node {
    public TrackSegment(NodeTracker tracker, int length) {
        super(tracker, length);
    }
}
