package simulation;

import model.Direction;
import model.control.TransitModel;
import model.node.Node;
import model.node.NodeLineProfile;
import model.train.Passenger;
import model.train.Train;
import model.train.track.TrackSegment;
import ticket.AdultTicket;
import ticket.Ticket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrainSimulator {

    private final int tickSpeed;

    public TrainSimulator(int tickSpeed) {
        this.tickSpeed = tickSpeed;
    }

    /**
     * Clears the trains in the model provided and creates new trains randomly on
     * each track loop.
     * @param model The model to create work on
     */
    public void recreateTrains(TransitModel model) {
        model.clearTrains();

        // Keep track of mapped track segments
        Set<TrackSegment> mapped = new HashSet<>();
        List<Set<TrackSegment>> loops = new ArrayList<>();

        for (Node value : model.getNodes().values()) {
            for (NodeLineProfile profile : value.getLineProfiles()) {
                List<TrackSegment> toMap = new ArrayList<>();
                toMap.add(profile.getTrack(Direction.FORWARD));
                toMap.add(profile.getTrack(Direction.BACKWARD));

                for (TrackSegment segment : toMap) {
                    if (mapped.contains(segment)) continue;

                    Set<TrackSegment> loop = new HashSet<>();

                    loop.add(segment);
                    loop.addAll(segment.getNextTrackSegments(Direction.FORWARD));
                    loop.addAll(segment.getNextTrackSegments(Direction.BACKWARD));

                    mapped.addAll(loop);

                    loops.add(loop);

                }
            }
        }

        int trainNum = 1;

        for (Set<TrackSegment> loop : loops) {
            // loop will never be empty as when it is constructed
            // at least one is always added

            int trainsToSpawn = loop.size() / 4;
            trainsToSpawn = Math.max(1, trainsToSpawn);

            List<TrackSegment> list = new ArrayList<>(loop);

            for (int i = 0; i < trainsToSpawn; i++) {
                int index = (int) (Math.random() * list.size());
                TrackSegment segment = list.get(index);
                if (!segment.isEmpty()) continue;
                model.createTrain(segment, "Train " + trainNum++, Train.DEFAULT_CAPACITY);
            }
        }
    }

    public void tick(TransitModel model) {
        for (Train train : model.getTrainList()) {

            boolean wasAtStation = train.getPosition().getTrack()
                    .getNode()
                    .isPresent();

            // Move the train a bit
            train.move(Direction.FORWARD, Train.MAX_SPEED / tickSpeed);

            boolean nowAtStation = train.getPosition().getTrack()
                    .getNode()
                    .isPresent();

            if (nowAtStation && !wasAtStation) {
                // The train is arriving at a station
                // Unload passengers
                simulateAlighting(train);
            } else if (wasAtStation && !nowAtStation) {
                // The train is leaving a station
                // Load passengers
                simulateBoarding(train);
            }

        }
    }

    /**
     * Handle the alighting passengers on this train.
     *
     * @return the number of passengers that alighted
     */
    private int simulateAlighting(Train train) {
        int counter = 0;

        List<Passenger> passengerList = new ArrayList<>(train.getPassengerList());
        for (Passenger passenger : passengerList) {
            passenger.decrementStationsToTravel();

            if (passenger.shouldAlight()) {
                train.removePassenger(passenger);
                counter++;
            }
        }

        return counter;
    }

    /**
     * Handling simulating the boarding of passengers onto this train.
     * @return the number of passengers that boarded
     */
    private int simulateBoarding(Train train) {
        if (train.getPassengerList().size() >= train.getCapacity()) return 0;
        int numPassengersBoarded = 0;
        while (Math.random() < 0.5) {
            Ticket ticket = new AdultTicket();
            int stationsToTravel = (int) (Math.random() * 5) + 1;
            Passenger passenger = new Passenger(ticket, stationsToTravel);
            train.addPassenger(passenger);
            numPassengersBoarded++;
        }
        return numPassengersBoarded;
    }
}