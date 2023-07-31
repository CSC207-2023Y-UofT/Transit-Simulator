package model.train;

import model.train.track.TrackSegment;

import java.util.List;

/**
 * The TrainTracker interface represents a system for managing trains and their positions on track segments.
 * It provides methods for creating new trains and retrieving a list of existing trains.
 */
public interface TrainTracker {

    /**
     * Creates a new train on the specified track segment with the given capacity.
     *
     * @param trackSegment The TrackSegment on which the new train will be created.
     * @param capacity     The maximum capacity of the new train, representing the maximum number of passengers it can carry.
     * @return The newly created Train object representing the new train on the track segment.
     */
    Train createTrain(TrackSegment trackSegment, int capacity);

    /**
     * Retrieves a list of all existing trains in the train tracker.
     *
     * @return A List containing all the Train objects currently present in the train tracker.
     */
    List<Train> getTrainList();
}
