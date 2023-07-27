package model.train;

import java.util.Map;

public interface TrackRepo {
    Map<String, Track> getTracks();
    Track getTrack(String id);
    Track createTrack(String id, int length);
}
