package model.node;

import util.Preconditions;

import java.util.*;




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
