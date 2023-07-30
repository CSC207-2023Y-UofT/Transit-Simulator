package model.train;

import model.Direction;
import model.train.Train;
import model.train.track.TrackSegment;

import java.util.List;

public interface TrainTracker {
    Train createTrain(TrackSegment trackSegment, Direction direction, int capacity);
    List<Train> getTrains();
}
