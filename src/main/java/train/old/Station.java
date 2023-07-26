package train.old;

import org.jetbrains.annotations.NotNull;  // This was automatically suggested by IntelliJ, idk what it does
import train.Node;
import train.NodeTracker;
import train.Train;

import java.util.Map;
import java.util.TreeMap;

public class Station extends Node {

    public static int STATION_LENGTH = 50;

    public Station(NodeTracker tracker, int length) {
        super(tracker, length);

    }
}
