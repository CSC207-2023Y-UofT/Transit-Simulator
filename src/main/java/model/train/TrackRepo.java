package model.train;

import model.train.track.TrackSegment;

import java.util.Map;
import java.util.Optional;

public interface TrackRepo {
    Map<String, TrackSegment> getTracks();
    Optional<TrackSegment> getTrack(String id);
    void addTrack(TrackSegment segment);
}
