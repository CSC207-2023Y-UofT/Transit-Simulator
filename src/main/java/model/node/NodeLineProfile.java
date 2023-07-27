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

        tracks.values().forEach(trackRepo::addTrack);

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

    public List<TrainArrival> nextArrivals(Direction direction, int numTrains) {
        List<TrackSegment> trackSegments = getTrack(direction)
                .getNextTrackSegments(direction.opposite());

        List<TrainArrival> arrivals = new ArrayList<>();

        double waitTime = 0;


        for (TrackSegment trackSegment : trackSegments) {
            if (trackSegment.getTrain() == null) continue;

            Train train = trackSegment.getTrain();

            // Calculate the added wait time
            if (trackSegment instanceof NodeTrackSegment) {
                // The train will probably stop here, so add the extra
                // time that will be spent at the node/station
                waitTime += Train.STATION_WAIT_TIME;
            }
            waitTime += trackSegment.getLength() / (double) Train.MAX_SPEED;

            TrainArrival arrival = new TrainArrival(train, node, (long) waitTime);
            arrivals.add(arrival);
        }

        return arrivals;
    }
}
