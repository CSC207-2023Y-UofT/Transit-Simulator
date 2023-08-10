package entity.model.train.repo;

import entity.model.train.track.TrackSegment;

import java.util.Map;
import java.util.Optional;

/**
 * The TrackRepo interface represents a repository for managing track segments in a track system.
 * It provides methods to interact with the track segments in the repository.
 */
public interface TrackRepo {

    /**
     * Retrieves a map of all track segments in the repository.
     *
     * @return A map where the keys are the track segment IDs and the values are the corresponding TrackSegment objects.
     */
    Map<String, TrackSegment> getTracks();

    /**
     * Retrieves the track segment with the specified ID, if it exists in the repository.
     *
     * @param id The ID of the track segment to retrieve.
     * @return An Optional containing the TrackSegment object with the specified ID if found, or an empty Optional otherwise.
     */
    Optional<TrackSegment> getTrack(String id);

    /**
     * Adds a new track segment to the repository.
     *
     * @param segment The TrackSegment object to add to the repository.
     */
    void addTrack(TrackSegment segment);

}
