package model.train;

import model.*;
import model.control.TransitModel;
import employee.Employee;
import model.node.Node;
import model.train.track.TrackSegment;
import util.Preconditions;

import java.util.*;

/**
 * The Train class represents a train in a transportation system.
 * It contains information about the train's status, position, passengers, staff, and associated transit tracker.
 * Trains can move along tracks in both forward and backward directions and can carry passengers.
 */
public class Train {

    /**
     * The default capacity of a train.
     */
    public static final int DEFAULT_CAPACITY = 100;

    /**
     * Enum representing the possible statuses of a train.
     * <br>
     * <p>
     * IN_SERVICE:            Running properly.
     * SCHEDULED_MAINTENANCE: still running, will stop for maintenance at the next station. (not automatically under maintenance)
     * UNDER_MAINTENANCE:     stopped for maintenance at a station and staff assigned to maintain.
     * OUT_OF_SERVICE:        Not running.
     * <br>
     * <p>
     * Precondition: Trains UNDER_MAINTENANCE cannot be IN_SERVICE.
     * Trains that are running can pass Offline trains whether at tracks or at stations. IRL justification: most
     * tracks have spare tracks that others can pass.
     */
    public enum Status {
        IN_SERVICE,
        SCHEDULED_MAINTENANCE,
        UNDER_MAINTENANCE,
        OUT_OF_SERVICE
    }

    /**
     * The maximum length of the train in meters.
     */
    public static int LENGTH = 100;

    /**
     * The maximum speed of the train in meters per second. This is approximately 115 km/h.
     */
    public static double MAX_SPEED = 1600;

    /**
     * The waiting time in milliseconds at a station before the train departs. This is set to 20 seconds.
     */
    public static long STATION_WAIT_TIME = 1000 * 20; // 20 seconds

    /**
     * The associated TransitTracker for this train.
     */
    private final TransitModel transitModel;

    /**
     * The name of this train.
     */
    private final String name;

    /**
     * The set containing the list of passengers currently on this train.
     */
    private final Set<Passenger> passengerList = new HashSet<>();

    /**
     * The maximum capacity of this train, representing the maximum number of passengers it can carry.
     */
    private final int capacity;

    /**
     * The current status of this train (IN_SERVICE, SCHEDULED_MAINTENANCE, UNDER_MAINTENANCE, OUT_OF_SERVICE).
     * Default status is OUT_OF_SERVICE.
     */
    private Status status = Status.OUT_OF_SERVICE;

    /**
     * The current position of this train represented by a TrainPosition object.
     */
    private TrainPosition position;

    /**
     * Creates a train associated with the given TransitTracker, positioned at the given TrainPosition, and with the given capacity.
     *
     * @param transitModel The TransitTracker that this train is associated with.
     * @param position     The position of this train.
     * @param capacity     The capacity of this train.
     */
    public Train(TransitModel transitModel, String name, TrainPosition position, int capacity) {
        this.transitModel = transitModel;
        this.name = name;
        this.position = position;
        this.capacity = capacity;

        Preconditions.checkArgument(position.getTrack().isEmpty(),
                "Train cannot be created on a non-empty track segment.");

        position.getTrack().setTrain(this);
    }

    public String getName() {
        return name;
    }

    /**
     * Gets the status of this train.
     *
     * @return The current status of this train.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of this train.
     *
     * @param status The new status of this train.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets the list of passengers on this train.
     *
     * @return An unmodifiable set containing the passengers on this train.
     */
    public Set<Passenger> getPassengerList() {
        return Collections.unmodifiableSet(passengerList);
    }

    /**
     * Adds a passenger to this train.
     *
     * @param passenger The passenger to add.
     * @throws IllegalStateException if the train is full and cannot accept more passengers.
     */
    public void addPassenger(Passenger passenger) {
        Preconditions.checkState(passengerList.size() < capacity, "Train is full");
        passengerList.add(passenger);
    }

    /**
     * Directly removes and returns the specified passenger from this train.
     *
     * @param passenger The passenger to remove.
     * @return true if the passenger was removed, false if the passenger was not on this train.
     */
    public boolean removePassenger(Passenger passenger) {
        return passengerList.remove(passenger);
    }


    /**
     * Get the transit tracker that this train is associated with.
     *
     * @return the TransitTracker that this train is associated with
     */
    public TransitModel getTransitTracker() {
        return transitModel;
    }

    /**
     * Gets the capacity of this train.
     *
     * @return The maximum capacity of this train.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets the position of this train.
     *
     * @return The current TrainPosition of this train.
     */
    public TrainPosition getPosition() {
        return position;
    }

    /**
     * Sets the position of this train.
     *
     * @param position The new TrainPosition for this train.
     */
    protected void setPosition(TrainPosition position) {

        if (!position.getTrack().isEmpty()) {
            if (position.getTrack().getTrain() != this) {
                throw new IllegalArgumentException(
                        "Train cannot be placed on a track segment that is already occupied by another train."
                );
            }
        }

        this.position.getTrack().setTrain(null);
        this.position = position;
        this.position.getTrack().setTrain(this);
    }

    /**
     * Get the next node that this train will move to, excluding
     * the current node if this train is already at a node.
     * <p>
     * If this train is not on a track, or there is no next node,
     * the returned Optional will be empty.
     *
     * @param direction The direction to look for the next node.
     * @return An Optional containing the next Node that this train will move to, or an empty Optional if there is no next node.
     */
    public Optional<Node> getNextNode(Direction direction) {
        TrackSegment track = position.getTrack();
        if (track == null) return Optional.empty();

        List<TrackSegment> nextSegments = track.getNextTrackSegments(direction);
        for (TrackSegment nextSegment : nextSegments) {
            Optional<Node> node = nextSegment.getNode();
            if (node.isPresent()) {
                return node;
            }
        }

        return Optional.empty();
    }

    /**
     * Get the distance to the next node that this train will move to, excluding
     * the current node if this train is already at a node.
     * <p>
     * If this train is not on a track, or there is no next node,
     * the returned Optional will be empty.
     * <p>
     * Gets the distance to the next node that this train will move to, excluding the current node if this train is already at a node.
     * If this train is not on a track or there is no next node, the returned Optional will be empty.
     *
     * @param direction The direction to look for the next node.
     * @return An Optional containing the distance to the next node, or an empty Optional if there is no next node.
     */
    public Optional<Double> getDistanceToNextNode(Direction direction) {

        TrackSegment track = position.getTrack();
        if (track == null) return Optional.empty();

        double distance = position.distanceToEndOfTrack(direction);

        List<TrackSegment> nextSegments = track.getNextTrackSegments(direction);

        for (TrackSegment nextSegment : nextSegments) {
            Optional<Node> node = nextSegment.getNode();
            if (node.isPresent()) {
                return Optional.of(distance);
            } else {
                distance += nextSegment.getLength();
            }
        }

        return Optional.empty();
    }

    /**
     * Moves the train in the given direction by the given amount.
     *
     * @param direction The direction to move the train.
     * @param amount    The amount to move the train by.
     * @return true if the train was moved, false if the train could not be moved.
     */
    public boolean move(Direction direction, double amount) {
        Preconditions.checkArgument(amount >= 0, "amount must be non-negative");
        amount = amount * direction.getMultiplier();

        TrainPosition movedPosition = position.move(amount, false)
                .orElse(null);

        if (movedPosition == null) return false;

        // Then move the train
        setPosition(movedPosition);

        return true;
    }
}
