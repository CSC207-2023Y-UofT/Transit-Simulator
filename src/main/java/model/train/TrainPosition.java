package model.train;

import model.Direction;
import model.train.track.TrackSegment;
import util.Preconditions;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents a position of a train on a track.
 */
public class TrainPosition {

    /**
     * The track that the train is on.
     */
    private final TrackSegment track;

    /**
     * The position of the train on the track.
     */
    private final double positionOnTrack;

    /**
     * Constructs a new TrainPosition on the given track at the given position.
     * @param track the track
     * @param positionOnNode the position on the track
     */
    public TrainPosition(TrackSegment track, double positionOnNode) {
        Preconditions.checkArgument(
                positionOnNode >= 0 && positionOnNode <= track.getLength(),
                "Position on track must be between 0 and the track's length"
        );
        this.track = track;
        this.positionOnTrack = positionOnNode;
    }

    /**
     * Gets the track segment on which the train is currently located.
     *
     * @return The TrackSegment the train is on.
     */
    public TrackSegment getTrack() {
        return track;
    }

    /**
     * Gets the position of the train on the track.
     *
     * @return The position of the train on the track.
     */
    public double getPositionOnTrack() {
        return positionOnTrack;
    }


    /**
     * Creates a new TrainPosition with an offset from the beginning of the track.
     *
     * @param offset The offset to be added to the current position.
     * @return A new TrainPosition with the specified offset.
     * @throws IllegalArgumentException if the resulting position would be outside the valid range [0, track.getLength()].
     */
    public TrainPosition withOffset(double offset) {

        Preconditions.checkArgument(
                offset >= 0 && offset <= track.getLength(),
                "Offset must be between 0 and the track's length"
        );
        return new TrainPosition(track, offset);
    }

    /**
     * Calculates the offset from the current position to the end of the track in the specified direction.
     *
     * @param direction The direction to calculate the offset towards (FORWARD or BACKWARD).
     * @return The offset in meters to the end of the track in the specified direction.
     */
    public double trackEndOffset(Direction direction) {
        if (direction == Direction.FORWARD) {
            return track.getLength() - positionOnTrack;
        } else {
            return -positionOnTrack;
        }
    }

    /**
     * Calculates the distance from the current position to the end of the track in the specified direction.
     *
     * @param direction The direction to calculate the distance towards (FORWARD or BACKWARD).
     * @return The distance in meters to the end of the track in the specified direction.
     */
    public double distanceToEndOfTrack(Direction direction) {
        return Math.abs(trackEndOffset(direction));
    }

    /**
     * Returns a new TrainPosition representing a new position that is moved
     * by the given amount. If the amount is negative, the position will be
     * moved backwards. The new position may be on a new track, or the returned
     * Optional may be empty if an endpoint was reached.
     * @param amount The amount to move by
     * @return A new TrainPosition representing the moved position, or an empty
     *       Optional if an endpoint was reached.
     */
    public Optional<TrainPosition> move(double amount) {
        return move(amount, true);
    }

    /**
     * Returns a new TrainPosition representing a new position that is moved
     * by the given amount. If the amount is negative, the position will be
     * moved backwards. The new position may be on a new track, or the returned
     * Optional may be empty if an endpoint was reached or if noClip is false
     * and an occupied track was encountered.
     *
     * @param amount The amount to move by
     * @param noClip Whether to ignore occupied tracks and treat them as unoccupied.
     * @return A new TrainPosition representing the moved position, or an empty
     *        Optional if an endpoint was reached or if noClip is false and an
     *        occupied track was encountered.
     */
    public Optional<TrainPosition> move(double amount, boolean noClip) {
        if (amount == 0.0) return Optional.of(this);

        Direction direction = amount < 0.0 ? Direction.BACKWARD : Direction.FORWARD;


        double movement = amount;
        double relativeTarget = positionOnTrack + movement;

        TrainPosition currPosition = this;

        // While the target is not within the bounds of
        // the current track, change which track we are on
        while (!(relativeTarget >= 0) || !(relativeTarget < currPosition.getTrack().getLength())) {

            TrackSegment next = currPosition.getTrack().getNext(direction);

            if (next == null) {
                return Optional.empty(); // Endpoint reached
            }

            if (!next.isEmpty() && !noClip) {
                return Optional.empty(); // Collision
            }

            double nextTrackOffset = currPosition.trackEndOffset(direction);

            currPosition = TrainPosition.entryPoint(next, direction);

            movement -= nextTrackOffset;
            relativeTarget = currPosition.positionOnTrack + movement;
        }

        return Optional.of(currPosition.withOffset(relativeTarget));
    }

    /**
     * Checks if this TrainPosition object is equal to another object.
     *
     * @param obj The object to compare with this TrainPosition.
     * @return true if the given object is a TrainPosition and has the same track and position on the track as this TrainPosition, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TrainPosition) {
            TrainPosition other = (TrainPosition) obj;
            return track.equals(other.track) && positionOnTrack == other.positionOnTrack;
        }
        return false;
    }

    /**
     * Generates a hash code for this TrainPosition object.
     *
     * @return The hash code value for this TrainPosition, calculated based on its track and position on the track.
     */
    @Override
    public int hashCode() {
        return Objects.hash(track, positionOnTrack);
    }

    /**
     * Creates a new TrainPosition object representing the entry point of a track segment in the specified direction.
     *
     * @param track   The TrackSegment representing the entry point of the track.
     * @param heading The direction of the entry point (FORWARD or BACKWARD).
     * @return A new TrainPosition object representing the entry point of the track segment.
     */
    public static TrainPosition entryPoint(TrackSegment track, Direction heading) {
        return new TrainPosition(track, heading == Direction.FORWARD ? 0.0 : track.getLength());
    }

}
