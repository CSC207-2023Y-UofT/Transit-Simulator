package model.train;

import model.train.track.TrackSegment;

import java.util.Objects;

/**
 * Immutable representation of a train's position.
 */
public class TrainPosition {
    private final TrackSegment track;
    private final double positionOnTrack;

    public TrainPosition(TrackSegment track, double positionOnNode) {
        this.track = track;
        this.positionOnTrack = positionOnNode;
    }

    public TrackSegment getTrack() {
        return track;
    }

    public double getPositionOnTrack() {
        return positionOnTrack;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TrainPosition) {
            TrainPosition other = (TrainPosition) obj;
            return track.equals(other.track) && positionOnTrack == other.positionOnTrack;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(track, positionOnTrack);
    }
}
