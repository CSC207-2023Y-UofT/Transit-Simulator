package model.train;

import model.node.Node;

import java.util.Objects;

/**
 * Immutable representation of a train's position.
 */
public class TrainPosition {
    private final Track track;
    private final double positionOnTrack;

    public TrainPosition(Track track, double positionOnNode) {
        this.track = track;
        this.positionOnTrack = positionOnNode;
    }

    public Track getTrack() {
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
