package model.node;

import model.node.Node;
import model.node.NodeTracker;

public class Station extends Node {

    public static int STATION_LENGTH = 50;

    public Station(NodeTracker tracker, int length) {
        super(tracker, length);

    }
}
