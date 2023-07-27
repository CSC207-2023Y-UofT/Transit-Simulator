package model.train;

import model.Direction;
import model.train.track.TrackSegment;
import model.util.Preconditions;

import java.util.Objects;
import java.util.Optional;

/**
 * Immutable representation of a train's position.
 */
public class TrainPosition {
    private final TrackSegment track;
    private final double positionOnTrack;

    public TrainPosition(TrackSegment track, double positionOnNode) {
        Preconditions.checkArgument(
                positionOnNode >= 0 && positionOnNode <= track.getLength(),
                "Position on track must be between 0 and the track's length"
        );
        this.track = track;
        this.positionOnTrack = positionOnNode;
    }

    public TrackSegment getTrack() {
        return track;
    }

    public double getPositionOnTrack() {
        return positionOnTrack;
    }

    public TrainPosition withOffset(double offset) {
        Preconditions.checkArgument(
                offset >= 0 && offset <= track.getLength(),
                "Offset must be between 0 and the track's length"
        );
        return new TrainPosition(track, offset);
    }

    public double trackEndOffset(Direction direction) {
        if (direction == Direction.FORWARD) {
            return track.getLength() - positionOnTrack;
        } else {
            return -positionOnTrack;
        }
    }

    public double distanceToEndOfTrack(Direction direction) {
        return Math.abs(trackEndOffset(direction));
    }

    public Optional<TrainPosition> move(double amount) {
        if (amount == 0.0) return Optional.of(this);

        Direction direction = amount < 0.0 ? Direction.BACKWARD : Direction.FORWARD;

        double target = positionOnTrack + amount;
        TrainPosition currPosition = this;

        // While the target is not within the bounds of
        // the current track, change which track we are on
        while (!(target >= 0) || !(target < currPosition.getTrack().getLength())) {

            TrackSegment next = currPosition.getTrack().getNext(direction);

            if (next == null) {
                return Optional.empty(); // Endpoint reached
            }

            double nextTrackOffset = currPosition.trackEndOffset(direction);

            target -= nextTrackOffset;

            currPosition = TrainPosition.entryPoint(next, direction);
        }

        return Optional.of(currPosition.withOffset(target));
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

    public static TrainPosition entryPoint(TrackSegment track, Direction heading) {
        return new TrainPosition(track, heading == Direction.FORWARD ? 0.0 : track.getLength());
    }
}
