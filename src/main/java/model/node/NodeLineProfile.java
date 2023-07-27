package model.node;

import model.Direction;
import model.train.TrackRepo;
import model.train.Train;
import model.train.track.NodeTrackSegment;
import model.train.track.TrackSegment;
import org.jetbrains.annotations.NotNull;

import javax.sound.midi.Track;
import java.util.*;

public class NodeLineProfile {
    private final Node node;
    private final int lineNumber;

    private final Map<Direction, TrackSegment> tracks = new HashMap<>();

    public NodeLineProfile(Node node, int lineNumber) {
        this.node = node;
        this.lineNumber = lineNumber;

        // Create the tracks in each direction
        TrackRepo trackRepo = node.getTracker().getTrackRepo();
        for (Direction value : Direction.values()) {
            tracks.put(
                    value,
                    new NodeTrackSegment(
                            trackRepo,
                            node,
                            node.getName() + "-" + lineNumber + "-" + value,
                            Train.LENGTH
                    )
            );
        }

    }

    public int getLineNumber() {
        return lineNumber;
    }

    public @NotNull Node getNode() {
        return node;
    }

    public @NotNull TrackSegment getTrack(Direction direction) {
        return tracks.get(direction);
    }

    /**
     * Returns the next {@code numTrains} trains that will arrive at this station in the given {@code direction}.
     *
     * @param direction the direction the trains are moving
     * @param numTrains the maximum number of trains to return
     * @throws IllegalStateException if the network of nodes this node is a part of is cyclic
     */
    public List<Train> nextArrivals(Direction direction, int numTrains) {
        List<TrackSegment> trackSegments = getTrack(direction)
                .getNextTrackSegments(direction.opposite());

        List<Train> trains = new ArrayList<>();
        for (TrackSegment trackSegment : trackSegments) {
            if (trackSegment instanceof NodeTrackSegment) {
                NodeTrackSegment nodeTrackSegment = (NodeTrackSegment) trackSegment;
                Train train = nodeTrackSegment.getTrain();
                if (train != null) {
                    trains.add(train);
                }
            }
        }
    }
}
