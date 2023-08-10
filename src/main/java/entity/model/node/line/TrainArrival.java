package entity.model.node.line;

import entity.model.node.Node;
import entity.model.train.Train;

/**
 * The TrainArrival class represents an immutable data class that holds information about a train's arrival at a node.
 * It contains details such as the train, the node where it arrives, and the delay (if any) in the arrival time.
 */
public class TrainArrival {

    /**
     * The Train object representing the arriving train.
     */
    private final Train train;
    /**
     * The Node object representing the node where the train arrives.
     */
    private final Node node;
    /**
     * The delay in the arrival time, represented in milliseconds.
     */
    private final long delay;

    /**
     * Constructs a new TrainArrival object with the specified train, node, and delay.
     *
     * @param train The Train object representing the arriving train.
     * @param node  The Node object representing the node where the train arrives.
     * @param delay The delay in the arrival time, represented in milliseconds.
     */
    public TrainArrival(Train train, Node node, long delay) {
        this.train = train;
        this.node = node;
        this.delay = delay;
    }

    /**
     * Retrieves the Node where the train arrives.
     *
     * @return The Node object representing the arrival node.
     */
    public Node getNode() {
        return node;
    }

    /**
     * Retrieves the delay in the train's arrival time.
     *
     * @return The delay in milliseconds.
     */
    public long getDelay() {
        return delay;
    }

    /**
     * Retrieves the Train object representing the arriving train.
     *
     * @return The Train object associated with the arrival.
     */
    public Train getTrain() {
        return train;
    }

}