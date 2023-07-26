package model.train;

import model.Direction;
import model.train.Train;

public interface TrainTracker {
    Train createTrain(String nodeId, Direction direction, int capacity);
}
