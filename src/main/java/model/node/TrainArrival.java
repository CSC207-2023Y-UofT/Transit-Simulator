package model.node;

import model.train.Train;

/**
 * Immutable data class representing a train's arrival time.
 */
public class TrainArrival {
    private Train train;
    private Node node;
    private long delay;

    public TrainArrival(Train train, Node node, long delay) {
        this.train = train;
        this.node = node;
        this.delay = delay;
    }

    public Node getNode() {
        return node;
    }

    public long getDelay() {
        return delay;
    }

    public Train getTrain() {
        return train;
    }
}
