package simulation;

import model.Direction;
import model.control.TransitModel;
import model.node.Node;
import model.node.NodeLineProfile;
import model.train.Passenger;
import model.train.Train;
import model.train.track.TrackSegment;
import stats.entry.impl.ElectricityUsageStat;
import stats.entry.impl.TicketSaleStat;
import stats.persistence.StatDataController;
import ticket.Ticket;
import ticket.TicketType;
import util.PerlinNoise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class that simulates the trains in the transit system.
 */
public class TrainSimulator {

    /**
     * The number of ticks per second
     */
    private final int tickSpeed;
    private final StatDataController stats;
    private final PerlinNoise passengerNoise = new PerlinNoise(123, 256);
    private final PerlinNoise electricityNoise = new PerlinNoise(456, 256);
    private final List<Passenger> waitingPassengers = new ArrayList<>();
    private final int maxWaitingPassengers = 100;
    private long tickNumber = 0;

    private double electricityAccumulator = 0.0;

    /**
     * Creates a new train simulator with the given tick speed.
     * @param tickSpeed The number of ticks per second
     */
    public TrainSimulator(int tickSpeed, StatDataController stats) {
        this.tickSpeed = tickSpeed;
        this.stats = stats;
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

            int trainsToSpawn = loop.size() / 6;
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

    /**
     * Simulate the boarding of passengers on this train.
     * @param model The model to simulate on
     */
    public void tick(TransitModel model) {

        for (Train train : model.getTrainList()) {

            boolean wasAtStation = train.getPosition().getTrack()
                    .getNode()
                    .isPresent();

            // Move the train a bit
            train.move(Direction.FORWARD, Train.MAX_SPEED / tickSpeed);

            // Record electric use
            if (!wasAtStation) {
                electricityAccumulator += (passengerNoise.noise(tickNumber / 100.0) + 1.0) * 0.02;
            }

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

        if (tickNumber % 50 == 0) {
            addWaitingPassengers();

            ElectricityUsageStat electricityUsageStat = new ElectricityUsageStat(electricityAccumulator);
            stats.record(electricityUsageStat);
            electricityAccumulator = 0.0;
        }

        tickNumber++;
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

        for (int i = 0; i < waitingPassengers.size(); i++) {
            Passenger passenger = waitingPassengers.get(i);
            if (train.getPassengerList().size() >= train.getCapacity()) break;
            train.addPassenger(passenger);
            waitingPassengers.remove(passenger);
            numPassengersBoarded++;
        }

        return numPassengersBoarded;
    }

    private void addWaitingPassengers() {

        double noise = this.passengerNoise.noise(tickNumber / 100.0);
        noise += 1.0;

        int numToAdd = (int) (noise * 20);
        for (int i = 0; i < numToAdd; i++) {
            if (waitingPassengers.size() >= maxWaitingPassengers) {
                break;
            }

            Ticket ticket = new Ticket(
                    TicketType.values()[(int) (Math.random() * TicketType.values().length)]
            );

            TicketSaleStat stat = new TicketSaleStat(ticket);
            stats.record(stat);

            waitingPassengers.add(new Passenger(ticket, (int) (Math.random() * 4)));
        }

    }
}
