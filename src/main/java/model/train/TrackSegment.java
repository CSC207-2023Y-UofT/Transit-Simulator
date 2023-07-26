package model.train;

import model.node.Node;
import model.node.NodeTracker;

import java.util.Random;

public class TrackSegment extends Node {
    public TrackSegment(NodeTracker tracker, int length) {
        super(tracker, "track_seg-" + new Random().nextInt(999999999), length);
    }
}
