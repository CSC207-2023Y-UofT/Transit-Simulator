package model.node;

import util.Preconditions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Abstract Node class represents a node in a transit system. Each node has a name,
 * belongs to a tracker, and can be associated with multiple line profiles.
 */
public abstract class Node {

    /**
     * The NodeTracker instance to which this node belongs.
     */
    private final NodeTracker tracker;

    /**
     * The name of the node.
     */
    private final String name;

    /**
     * The x coordinate of the node.
     */
    private int x = 0;

    /**
     * The y coordinate of the node.
     */
    private int y = 0;

    /**
     * Map containing NodeLineProfile instances associated with this node, keyed by line number.
     */
    private final Map<Integer, NodeLineProfile> lineProfiles = new HashMap<>();

    /**
     * Constructs a Node with the given tracker and name.
     *
     * @param tracker the NodeTracker for this node
     * @param name    the name of this node
     */
    public Node(NodeTracker tracker, String name) {
        this.tracker = tracker;
        this.name = name;
    }

    /**
     * Sets the y coordinates of this node.
     *
     * @param x The x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the x coordinates of this node.
     *
     * @param y The y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the x coordinates of this node.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinates of this node.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the NodeTracker for this node.
     *
     * @return the NodeTracker for this node
     */
    public NodeTracker getTracker() {
        return tracker;
    }

    /**
     * Gets the name of this node.
     *
     * @return the name of this node
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the NodeLineProfile associated with a specific line number, if it exists.
     *
     * @param lineNumber the line number for the NodeLineProfile
     * @return an Optional that may contain the NodeLineProfile for the given line number
     */
    public Optional<NodeLineProfile> getLineProfile(int lineNumber) {
        return Optional.ofNullable(lineProfiles.get(lineNumber));
    }

    /**
     * Gets all NodeLineProfiles associated with this node.
     *
     * @return a Collection of all NodeLineProfiles associated with this node
     */
    public Collection<NodeLineProfile> getLineProfiles() {
        return lineProfiles.values();
    }

    /**
     * Creates a new NodeLineProfile for a specific line number and associates it with this node.
     *
     * @param lineNumber the line number for the new NodeLineProfile
     * @return the newly created NodeLineProfile
     * @throws IllegalArgumentException if the line number is not positive or already exists
     */
    public NodeLineProfile createLineProfile(int lineNumber) {
        Preconditions.checkArgument(lineNumber > 0, "Line number must be positive");
        Preconditions.checkArgument(!lineProfiles.containsKey(lineNumber), "Line number already exists");
        NodeLineProfile profile = new NodeLineProfile(this, lineNumber);
        lineProfiles.put(lineNumber, profile);
        return profile;
    }

}