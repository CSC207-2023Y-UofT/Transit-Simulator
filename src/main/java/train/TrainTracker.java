package train;

public interface TrainTracker {
    Train createTrain(String nodeId, Direction direction, int capacity);
}
