package entity.model.train;

import entity.model.train.track.TrackSegment;

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
    Train createTrain(TrackSegment trackSegment, String name, int capacity);

    /**
     * Retrieves a list of all existing trains in the train tracker.
     *
     * @return A List containing all the Train objects currently present in the train tracker.
     */
    List<Train> getTrainList();

    /**
     * Retrieves the train with the specified name, if it exists in the train tracker.
     *
     * @param name The name of the train to retrieve.
     * @return The Train object with the specified name if found, or null otherwise.
     */
    Train getTrain(String name);

    /**
     * Removes the train with the specified name from the train tracker.
     *
     * @param name The name of the train to remove.
     */
    void removeTrain(String name);

    /**
     * Removes all trains from the train tracker.
     */
    void clearTrains();
}
