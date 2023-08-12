package simulation;

import entity.model.Direction;
import entity.model.control.TransitModel;
import entity.model.node.Node;
import entity.model.node.line.NodeLineProfile;
import entity.model.train.Passenger;
import entity.model.train.Train;
import entity.model.train.track.TrackSegment;
import stats.entry.impl.expense.ElectricityUsageStat;
import stats.entry.impl.revenue.TicketSaleStat;
import stats.StatDataController;
import entity.ticket.Ticket;
import entity.ticket.TicketType;
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

    /**
     * Stat data controller
     */
    private final StatDataController stats;

    /**
     * The noise generator used for passenger simulation
     */
    private final PerlinNoise passengerNoise = new PerlinNoise(123, 256);

    /**
     * The noise generator used for electricity usage simulation
     */
    private final PerlinNoise electricityNoise = new PerlinNoise(456, 256);

    /**
     * A list of waiting passengers that will board the next available train
     * that arrives at <b>any</b> station.
     */
    private final List<Passenger> waitingPassengers = new ArrayList<>();

    /**
     * The current tick number
     */
    private long tickNumber = 0;

    /**
     * An accumulator for electricity usage so that we don't crowd statistics with millions
     * of ElectricityUsageStat stats. This will be pushed and cleared on an interval.
     */
    private double electricityAccumulator = 0.0;

    /**
     * Creates a new train simulator with the given tick speed.
     *
     * @param tickSpeed The number of ticks per second
     */
    public TrainSimulator(int tickSpeed, StatDataController stats) {
        this.tickSpeed = tickSpeed;
        this.stats = stats;
    }

    /**
     * Clears the trains in the model provided and creates new trains randomly on
     * each track loop.
     *
     * @param model The model to create work on
     */
    public void recreateTrains(TransitModel model) {

        // Clear any existing trains
        model.clearTrains();

        // The following code will map the transit system and create a list
        // of track loops that will be used to randomly spawn trains such that
        // each loop has at least one train on it.

        // This will keep track of what track segments we've already looked at
        Set<TrackSegment> mapped = new HashSet<>();

        // This will keep track of each loop we discover
        List<Set<TrackSegment>> loops = new ArrayList<>();

        // Go through all nodes, so we don't miss any track loops
        for (Node value : model.getNodes().values()) {

            // For each line profile they have
            for (NodeLineProfile profile : value.getLineProfiles()) {

                // We will map the forward and backwards parts of this line
                List<TrackSegment> roots = new ArrayList<>();
                roots.add(profile.getTrack(Direction.FORWARD));
                roots.add(profile.getTrack(Direction.BACKWARD));

                for (TrackSegment segment : roots) {

                    // Don't map this if we've already been here
                    if (mapped.contains(segment)) continue;

                    // Keep track of the loop
                    Set<TrackSegment> loop = new HashSet<>();

                    // Add the root
                    loop.add(segment);

                    // Add all segments connected to this one in both directions
                    loop.addAll(segment.getNextTrackSegments(Direction.FORWARD));
                    loop.addAll(segment.getNextTrackSegments(Direction.BACKWARD));

                    // Record all of these track segments as mapped
                    mapped.addAll(loop);

                    // Add the loop to the list
                    loops.add(loop);
                }
            }
        }

        // The train-number of the next train to be spawned
        int trainNum = 1;

        for (Set<TrackSegment> loop : loops) {
            // loop will never be empty as when it is constructed
            // at least one is always added (the root)

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
     *
     * @param model The model to simulate on
     */
    public void tick(TransitModel model, double delta) {

        for (Train train : model.getTrainList()) {

            boolean wasAtStation = train.getPosition().getTrack()
                    .getNode()
                    .isPresent();

            // Move the train a bit
            train.move(Direction.FORWARD, Train.MAX_SPEED / tickSpeed * delta);

            // Record electric use
            if (!wasAtStation) {
                double noise = (electricityNoise.noise(tickNumber / 12000.0) + 1.0) * 0.06;
                electricityAccumulator += noise;
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

        if (tickNumber % 10 == 0) {
            addWaitingPassengers();
        }

        if (tickNumber % 40 == 0) {
            ElectricityUsageStat electricityUsageStat = new ElectricityUsageStat(electricityAccumulator);
            stats.record(electricityUsageStat);
            electricityAccumulator = 0.0;
        }

        tickNumber++;
    }

    /**
     * Handle the alighting passengers on this train.
     */
    private void simulateAlighting(Train train) {
        int counter = 0;

        List<Passenger> passengerList = new ArrayList<>(train.getPassengerList());
        for (Passenger passenger : passengerList) {
            passenger.decrementStationsToTravel();

            if (passenger.shouldAlight()) {
                train.removePassenger(passenger);
                counter++;
            }
        }

    }

    /**
     * Handling simulating the boarding of passengers onto this train.
     */
    private void simulateBoarding(Train train) {

        if (train.getPassengerList().size() >= train.getCapacity()) return;
        int numPassengersBoarded = 0;

        for (int i = 0; i < waitingPassengers.size(); i++) {
            Passenger passenger = waitingPassengers.get(i);
            if (train.getPassengerList().size() >= train.getCapacity()) break;
            train.addPassenger(passenger);
            waitingPassengers.remove(passenger);
            numPassengersBoarded++;
        }

    }

    /**
     * Add waiting passengers to the station.
     */
    private void addWaitingPassengers() {

        double noise = this.passengerNoise.noise(tickNumber / 12000.0);
        noise += 1.0;


        int numToAdd = (int) (noise * 10);
        for (int i = 0; i < numToAdd; i++) {
            int maxWaitingPassengers = 100;
            if (waitingPassengers.size() >= maxWaitingPassengers) {
                break;
            }

            // A random ticket type
            TicketType ticketType = TicketType.values()[(int) (Math.random() * TicketType.values().length)];
            Ticket ticket = new Ticket(ticketType);

            TicketSaleStat stat = new TicketSaleStat(ticket);
            stats.record(stat);

            waitingPassengers.add(new Passenger(ticket, (int) (Math.random() * 4)));
        }
    }

}
