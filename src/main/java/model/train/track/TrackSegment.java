package model.train.track;

import model.Direction;
import model.train.TrackRepo;
import model.train.Train;
import util.Preconditions;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class TrackSegment {
    private final String id;
    private final TrackRepo repo;
    private final int length;

    private TrackSegment next = null;
    private TrackSegment prev = null;
    private Train train = null;

    public TrackSegment(TrackRepo repo, String id, int length) {
        this.repo = repo;
        this.id = id;
        this.length = length;
    }

    public TrackRepo getRepo() {
        return repo;
    }

    public int getLength() {
        return length;
    }

    public String getId() {
        return id;
    }

    public TrackSegment getNext(Direction direction) {
        return direction == Direction.FORWARD ? getNext() : getPrev();
    }

    public TrackSegment getNext() {
        return next;
    }

    public TrackSegment getPrev() {
        return prev;
    }

    public void linkForward(TrackSegment next) {
        TrackSegment.link(this, next);
    }

    public void linkBackward(TrackSegment prev) {
        TrackSegment.link(prev, this);
    }

    /**
     * Gets all the next track segments in the given direction.
     * This method will return all track segments in the given direction
     * until either an endpoint is reached, or the track becomes cyclic.
     */
    public List<TrackSegment> getNextTrackSegments(Direction direction) {

        // Use a linked hash set so that contains() runs in O(1) and
        // the insertion order of the tracks is maintained.
        LinkedHashSet<TrackSegment> segments = new LinkedHashSet<>();
        TrackSegment next = this.getNext(direction);
        while (next != null && !segments.contains(next)) {
            segments.add(next);
            next = next.getNext(direction);
        }

        return new ArrayList<>(segments);
    }

    public boolean isEndpoint(Direction direction) {
        return getNext(direction) == null;
    }

    public boolean isEmpty() {
        return train == null;
    }


    /**
     * Gets the train on this track.
     *
     * @return the train on this track or null if there is no train on this track
     */
    public Train getTrain() {
        return train;
    }

    /**
     * Set the train on this track to {@code train}.
     *
     * @param train the train to put on this track
     *              or null if there is no train on this track
     * @throws IllegalStateException if there is already a train on this track
     *                               and {@code train} is not null
     */
    public void setTrain(Train train) {
        Preconditions.checkState(
                train == null || this.train == null,
                "Track already has a train"
        );
        this.train = train;
    }

    public static void link(TrackSegment prev, TrackSegment next) {
        if (prev.next == next && next.prev == prev) {
            // Already linked
            return;
        }

        Preconditions.checkState(prev.next != null, "prev is linked to a different track!");
        Preconditions.checkState(next.prev != null, "next is linked to a different track!");

        prev.next = next;
        next.prev = prev;
    }

    public static void unlink(TrackSegment prev, TrackSegment next) {
        if (prev.next != next && next.prev != prev) {
            // Already unlinked
            return;
        }

        Preconditions.checkState(prev.next == next, "prev is not linked to next!");
        Preconditions.checkState(next.prev == prev, "next is not linked to prev!");

        prev.next = null;
        next.prev = null;
    }
}
