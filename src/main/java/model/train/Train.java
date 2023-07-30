package model.train;

import model.*;
import model.control.TransitTracker;
import employee.Employee;
import model.node.Node;
import model.train.track.NodeTrackSegment;
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
     * Enum representing the possible statuses of a train.
     * IN_SERVICE: The train is in service and running properly.
     * SCHEDULED_MAINTENANCE: The train is still running but will stop for maintenance at the next station (not automatically under maintenance).
     * UNDER_MAINTENANCE: The train has stopped for maintenance at a station, and staff are assigned to maintain it.
     * OUT_OF_SERVICE: The train is not running and is out of service.
     * Precondition: A train in UNDER_MAINTENANCE status cannot be set to IN_SERVICE.
     * Trains that are running can pass Offline trains, whether on tracks or at stations. In real life, most
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
    public static int MAX_SPEED = 32;

    /**
     * The waiting time in milliseconds at a station before the train departs. This is set to 20 seconds.
     */
    public static long STATION_WAIT_TIME = 1000 * 20; // 20 seconds

    /**
     * The associated TransitTracker for this train.
     */
    private final TransitTracker transitTracker;

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
     * A mapping of TrainJobs to Employees assigned to this train.
     */
    private final Map<TrainJob, Employee> staff = new HashMap<>();

    /**
     * Creates a train associated with the given TransitTracker, positioned at the given TrainPosition, and with the given capacity.
     *
     * @param transitTracker The TransitTracker that this train is associated with.
     * @param position The position of this train.
     * @param capacity The capacity of this train.
     */
    public Train(TransitTracker transitTracker, TrainPosition position, int capacity) {
        this.transitTracker = transitTracker;
        this.position = position;
        this.capacity = capacity;
    }

    /**
     * Gets a mapping of TrainJobs to Employees assigned to this train.
     *
     * @return A map containing the TrainJobs as keys and the corresponding Employee objects as values.
     */
    public Map<TrainJob, Employee> getStaff() {
        return staff;
    }

    /**
     * Assigns an employee to a job on this train.
     *
     * @param job The TrainJob to assign the employee to.
     * @param employee The Employee to assign to the job.
     */
    public void setStaff(TrainJob job, Employee employee) {
        staff.put(job, employee);
    }

    /**
     * Gets the employee assigned to a job on this train.
     *
     * @param job The TrainJob to get the employee for.
     * @return The Employee assigned to the specified job, or null if no employee is assigned to that job.
     */
    public void getStaff(TrainJob job) {
        staff.get(job);
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
     * Gets the TransitTracker that this train is associated with.
     *
     * @return The TransitTracker associated with this train.
     */
    public TransitTracker getTransitTracker() {
        return transitTracker;
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
        this.position = position;
    }

    /**
     * Gets the next node that this train will move to, excluding the current node if this train is already at a node.
     *
     * If this train is not on a track or there is no next node, the returned Optional will be empty.
     *
     * @param direction The direction to look for the next node.
     * @return An Optional containing the next Node that this train will move to, or an empty Optional if there is no next node.
     */
    public Optional<Node> getNextNode(Direction direction) {
        TrackSegment track = position.getTrack();
        if (track == null) return Optional.empty();

        List<TrackSegment> nextSegments = track.getNextTrackSegments(direction);
        for (TrackSegment nextSegment : nextSegments) {
            if (nextSegment instanceof NodeTrackSegment) {
                Node node = ((NodeTrackSegment) nextSegment).getNode();
                return Optional.of(node);
            }
        }

        return Optional.empty();
    }

    /**
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
            if (nextSegment instanceof NodeTrackSegment) {
                return Optional.of(distance);
            }
            distance += nextSegment.getLength();
        }

        return Optional.empty();
    }

    /**
     * Moves the train in the given direction by the given amount.
     *
     * @param direction The direction to move the train.
     * @param amount The amount to move the train by.
     * @return true if the train was moved, false if the train could not be moved.
     */
    public boolean move(Direction direction, double amount) {
        Preconditions.checkArgument(amount >= 0, "amount must be non-negative");
        amount = amount * direction.getMultiplier();

        TrainPosition movedPosition = position.move(amount, false)
                .orElse(null);

        if (movedPosition == null) return false;

        position = movedPosition;
        return true;
    }
}
