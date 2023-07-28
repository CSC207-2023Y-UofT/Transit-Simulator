package model.train;

import model.train.track.TrackSegment;
import model.util.Preconditions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BasicTrackRepo implements TrackRepo {

    private final Map<String, TrackSegment> tracks = new HashMap<>();

    @Override
    public Map<String, TrackSegment> getTracks() {
        return Collections.unmodifiableMap(tracks);
    }

    @Override
    public Optional<TrackSegment> getTrack(String id) {
        return Optional.ofNullable(tracks.get(id));
    }

    @Override
    public void addTrack(TrackSegment segment) {
        Preconditions.checkArgument(!tracks.containsKey(segment.getId()), "Track already exists");
        tracks.put(segment.getId(), segment);
    }
}
