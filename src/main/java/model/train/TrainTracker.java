package model.train;

import model.Direction;
import model.train.Train;
import model.train.track.TrackSegment;

import java.util.List;

public interface TrainTracker {
    Train createTrain(TrackSegment trackSegment, int capacity);

    List<Train> getTrainList();

}
