package train;

import org.jetbrains.annotations.Nullable;

import train.util.Preconditions;

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
    private final int length;
    private Node prev;
    private Node next;
    private Map<Direction, Train> trains = new HashMap<>();

    public Node(NodeTracker tracker, int length) {
        this.tracker = tracker;
        this.length = length;
        this.prev = null;
        this.next = null;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public int getLength() {
        return length;
    }

    public boolean isEndpoint(Direction direction) {
        return getNextNode(direction) == null;
    }

    public @Nullable Node getNextNode(Direction direction) {
        return direction == Direction.FORWARD ? this.next : this.prev;
    }

    public @Nullable Train getTrain(Direction direction) {
        return this.trains.get(direction);
    }

    public NodeTracker getTracker() {
        return tracker;
    }

    protected void setPrev(Node prev) {
        Preconditions.checkArgument(
                next.getTracker() == this.getTracker(),
                "Cannot connect nodes from different networks"
        );
        this.prev = prev;
    }

    protected void setNext(Node next) {
        Preconditions.checkArgument(
                next.getTracker() == this.getTracker(),
                "Cannot connect nodes from different networks"
        );
        this.next = next;
    }

    /**
     * Returns the next {@code numTrains} trains that will arrive at this station in the given {@code direction}.
     *
     * @param direction the direction the trains are moving
     * @param numTrains the maximum number of trains to return
     * @throws IllegalStateException if the network of nodes this node is a part of is cyclic
     */
    public List<Train> nextArrivals(Direction direction, int numTrains) {
        List<Train> nextArrivals = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        nextArrivalsRecursive(direction, numTrains, nextArrivals, visited);
        return nextArrivals;
    }

    private void nextArrivalsRecursive(Direction direction,
                                       int numTrains,
                                       List<Train> accumulator,
                                       Set<Node> visited) {
        if (accumulator.size() >= numTrains) return;
        if (visited.contains(this)) return;
        visited.add(this);
        accumulator.add(this.trains.get(direction));
        Node nextNode = this.getNextNode(direction);
        if (nextNode == null) return;
        nextNode.nextArrivalsRecursive(direction, numTrains, accumulator, visited);
    }

    /**
     * Returns the next nodes in the given {@code direction}.
     *
     * @throws IllegalStateException if the network of nodes this node is a part of is cyclic
     */
    public List<Node> nextNodes(Direction direction) {
        LinkedHashSet<Node> nodes = new LinkedHashSet<>();
        nextNodesRecursive(direction, nodes);
        return new ArrayList<>(nodes);
    }

    private void nextNodesRecursive(Direction direction,
                                    LinkedHashSet<Node> accumulator) {
        if (accumulator.contains(this)) return;
        accumulator.add(this);
        Node nextNode = this.getNextNode(direction);
        if (nextNode == null) return;
        nextNode.nextNodesRecursive(direction, accumulator);
    }

}
