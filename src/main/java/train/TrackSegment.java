package train;

import org.jetbrains.annotations.NotNull;
import train.Node;
import train.NodeTracker;

import java.util.Random;
import java.util.UUID;

public class TrackSegment extends Node {
    public TrackSegment(NodeTracker tracker, int length) {
        super(tracker, "track_seg-" + new Random().nextInt(999999999), length);
    }
}
