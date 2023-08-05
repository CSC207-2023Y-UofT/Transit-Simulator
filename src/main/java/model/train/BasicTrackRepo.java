package model.train;

import model.train.track.TrackSegment;
import util.Preconditions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The BasicTrackRepo class represents a basic implementation of the TrackRepo interface.
 * It maintains a collection of track segments and provides methods to interact with them.
 */
public class BasicTrackRepo implements TrackRepo {

    /**
     * The map of track segments in the repository, where the keys are the track segment IDs and the values are the corresponding TrackSegment objects.
     */
    private final Map<String, TrackSegment> tracks = new HashMap<>();

    /**
     * Retrieves an unmodifiable map of all track segments in the repository.
     *
     * @return An unmodifiable map where the keys are the track segment IDs and the values are the corresponding TrackSegment objects.
     */
    @Override
    public Map<String, TrackSegment> getTracks() {
        return Collections.unmodifiableMap(tracks);
    }

    /**
     * Retrieves the track segment with the specified ID, if it exists in the repository.
     *
     * @param id The ID of the track segment to retrieve.
     * @return An Optional containing the TrackSegment object with the specified ID if found, or an empty Optional otherwise.
     */
    @Override
    public Optional<TrackSegment> getTrack(String id) {
        return Optional.ofNullable(tracks.get(id));
    }

    /**
     * Adds a new track segment to the repository.
     *
     * @param segment The TrackSegment object to add to the repository.
     * @throws IllegalArgumentException if a track segment with the same ID already exists in the repository.
     * @throws IllegalArgumentException if the provided TrackSegment belongs to another repository.
     */
    @Override
    public void addTrack(TrackSegment segment) {
        Preconditions.checkArgument(!tracks.containsKey(segment.getId()), "Track " + segment.getId() + " already exists");
        Preconditions.checkArgument(segment.getRepo() == this, "Track belongs to another repo");
        tracks.put(segment.getId(), segment);
    }
}
