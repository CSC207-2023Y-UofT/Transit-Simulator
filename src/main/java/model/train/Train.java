package model.train;

import model.*;
import model.control.TransitTracker;
import employee.Employee;
import model.node.Node;
import model.train.track.NodeTrackSegment;
import model.train.track.TrackSegment;
import util.Preconditions;

import java.util.*;

public class Train {

    /**
     * Either ONLINE, SCHEDULED_MAINTENANCE, UNDER_MAINTENANCE or OFFLINE.
     * Online: Running properly.
     * Scheduled_maintenance: still running, will stop for maintenance at the next station. (not automatically under maintenance)
     * Under_maintenance: stopped for maintenance at a station and staff assigned to maintain.
     * Offline: not running.
     * Precondition: train UNDER_MAINTENANCE cannot be ONLINE
     * Trains that are running can pass Offline trains whether at tracks or at stations. IRL justification: most
     * tracks have spare tracks that others can pass.
     */
    public enum Status {
        IN_SERVICE,
        SCHEDULED_MAINTENANCE,
        UNDER_MAINTENANCE,
        OUT_OF_SERVICE
    }

    public static int LENGTH = 100;
    public static int MAX_SPEED = 32; // m/s, this is about 115 km/h
    public static long STATION_WAIT_TIME = 1000 * 20; // 20 seconds

    private final TransitTracker transitTracker;
    private final List<Passenger> passengerList = new ArrayList<>();
    private final int capacity;
    private Status status = Status.OUT_OF_SERVICE;
    private TrainPosition position;
    private final Map<TrainJob, Employee> staff = new HashMap<>();

    /**
     *  Creates a train on the given track in the given direction with the given capacity.
     *  @param transitTracker the transit tracker that this train is associated with
     *  @param direction the direction that this train is travelling in
     *  @param position the position of this train
     *  @param capacity the capacity of this train
     */
    public Train(TransitTracker transitTracker, TrainPosition position, int capacity) {
        this.transitTracker = transitTracker;
        this.position = position;
        this.capacity = capacity;
    }

    /**
     *  Get a mapping of TrainJobs to Employees assigned to this train.
     *  @return the staff assigned to this train
     */
    public Map<TrainJob, Employee> getStaff() {
        return staff;
    }

    /**
     *  Assign an employee to a job on this train.
     *  @param job the job to assign the employee to
     *  @param employee the employee to assign to the job
     */
    public void setStaff(TrainJob job, Employee employee) {
        staff.put(job, employee);
    }

    /**
     *  Get the employee assigned to a job on this train.
     *  @param job the job to get the employee for
     */
    public void getStaff(TrainJob job) {
        staff.get(job);
    }

    /**
     *  Get the status of this train.
     *  @return the status of this train
     */
    public Status getStatus() {
        return status;
    }

    /**
     *  Set the status of this train.
     *  @param status the new status of this train
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     *  Get the list of passengers on this train.
     *  @return the list of passengers on this train
     */
    public List<Passenger> getPassengerList() {
        return Collections.unmodifiableList(passengerList);
    }

    /**
     * Adds a passenger to this train.
     *
     * @param passenger the passenger to add
     * @throws IllegalStateException if the train is full
     */
    public void addPassenger(Passenger passenger) {
        Preconditions.checkState(passengerList.size() < capacity, "Train is full");
        passengerList.add(passenger);
    }

    /**
     *  Get the transit tracker that this train is associated with.
     *  @return the transit tracker that this train is associated with
     */
    public TransitTracker getTransitTracker() {
        return transitTracker;
    }

    /**
     *  Get the capacity of this train.
     *  @return the capacity of this train
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     *  Get the position of this train.
     *  @return the position of this train
     */
    public TrainPosition getPosition() {
        return position;
    }

    /**
     *  Set the position of this train.
     *  @param position the new position of this train
     */
    protected void setPosition(TrainPosition position) {
        this.position = position;
    }

    /**
     * Get the next node that this train will move to, excluding
     * the current node if this train is already at a node.
     *
     * If this train is not on a track, or there is no next node,
     * the returned Optional will be empty.
     *
     * @param direction the direction to look for the next node
     * @return the next node, or empty if there is no next node
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
     * Get the distance to the next node that this train will move to, excluding
     * the current node if this train is already at a node.
     * If this train is not on a track, or there is no next node,
     * the returned Optional will be empty.
     *
     * @param direction the direction to look for the next node
     * @return the distance to the next node, or empty if there is no next node
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
     * Move the train in the given direction by the given amount.
     *
     * @param direction the direction to move the train
     * @param amount the amount to move the train by
     * @return true if the train was moved, false if the train could not be moved
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
