package model.node;

import org.jetbrains.annotations.Nullable;

import model.Direction;
import model.train.Train;
import model.util.Preconditions;

import java.util.*;



/*
 *  TODO: Node Updater
 *  Potential idea: an updater that goes around
 *
 *
 *  On train arrival : controller action:
 *  1) Update the train's location
 *  2) change its location if this is an endpoint station
 *  3) Handle offloading passengers
 *  4) Handle statistics
 *  5) Make train offline if end of day, or scheduled for maintenance
 *  5.1) Handle onboarding passengers
 *  5.5) Handle time
 *  6) Send train if online
 *
 */


public abstract class Node {
    private final NodeTracker tracker;
    private final String name;

    private final Map<Integer, NodeLineProfile> lineProfiles = new HashMap<>();

    public Node(NodeTracker tracker, String name) {
        this.tracker = tracker;
        this.name = name;
    }

    public NodeTracker getTracker() {
        return tracker;
    }

    public String getName() {
        return name;
    }

    public Optional<NodeLineProfile> getLineProfile(int lineNumber) {
        return Optional.ofNullable(lineProfiles.get(lineNumber));
    }

    public NodeLineProfile createLineProfile(int lineNumber) {
        Preconditions.checkArgument(lineNumber > 0, "Line number must be positive");
        Preconditions.checkArgument(!lineProfiles.containsKey(lineNumber), "Line number already exists");
        NodeLineProfile profile = new NodeLineProfile(this, lineNumber);
        lineProfiles.put(lineNumber, profile);
        return profile;
    }

}
