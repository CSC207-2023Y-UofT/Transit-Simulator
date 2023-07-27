package model.train;

import model.train.track.TrackSegment;

import java.util.Map;

public interface TrackRepo {
    Map<String, TrackSegment> getTracks();
    TrackSegment getTrack(String id);
    TrackSegment createTrack(String id, int length);
}
