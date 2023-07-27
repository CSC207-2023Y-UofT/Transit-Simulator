package model.train;

import model.util.Preconditions;

public class Track {
    private final String id;
    private final TrackRepo repo;
    private final int length;

    private Track next = null;
    private Track prev = null;
    private Train train = null;

    public Track(TrackRepo repo, String id, int length) {
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

    public Track getNext() {
        return next;
    }

    public Track getPrev() {
        return prev;
    }

    public void linkForward(Track next) {
        Track.link(this, next);
    }

    public void linkBackward(Track prev) {
        Track.link(prev, this);
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

    public static void link(Track prev, Track next) {
        if (prev.next == next && next.prev == prev) {
            // Already linked
            return;
        }

        Preconditions.checkState(prev.next != null, "prev is linked to a different track!");
        Preconditions.checkState(next.prev != null, "next is linked to a different track!");

        prev.next = next;
        next.prev = prev;
    }

    public static void unlink(Track prev, Track next) {
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
