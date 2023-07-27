package model.train;

public class Track {
    private final String id;
    private final TrackRepo repo;
    private int length;
    private Track next;
    private Track prev;

    private Train train = null;

    public Track(TrackRepo repo, String id, int length) {
        this.repo = repo;
        this.id = id;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public String getId() {
        return id;
    }
}
