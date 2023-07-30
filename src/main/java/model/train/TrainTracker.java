package model.train;

import model.Direction;
import model.train.Train;
import model.train.track.TrackSegment;

public interface TrainTracker {
    Train createTrain(TrackSegment trackSegment, int capacity);
}
