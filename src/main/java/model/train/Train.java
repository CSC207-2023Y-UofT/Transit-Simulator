package model.train;

import model.*;
import model.control.TransitTracker;
import model.staff.Employee;
import model.node.Node;
import model.train.track.NodeTrackSegment;
import model.train.track.TrackSegment;
import model.util.Preconditions;

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
     * tracks have spare tracks that others can pass
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
    private final Direction direction;
    private Status status = Status.OUT_OF_SERVICE;
    private TrainPosition position;
    private final Map<TrainJob, Employee> staff = new HashMap<>();

    public Train(TransitTracker transitTracker, Direction direction, TrainPosition position, int capacity) {
        this.transitTracker = transitTracker;
        this.position = position;
        this.capacity = capacity;
        this.direction = direction;
    }

    public Map<TrainJob, Employee> getStaff() {
        return staff;
    }

    public void setStaff(TrainJob job, Employee employee) {
        staff.put(job, employee);
    }

    public void getStaff(TrainJob job) {
        staff.get(job);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Passenger> getPassengerList() {
        return Collections.unmodifiableList(passengerList);
    }

    /**
     * Adds a passenger to the train
     *
     * @param passenger the passenger to add
     * @throws IllegalStateException if the train is full
     */
    public void addPassenger(Passenger passenger) {
        Preconditions.checkState(passengerList.size() < capacity, "Train is full");
        passengerList.add(passenger);
    }

    public TransitTracker getTransitTracker() {
        return transitTracker;
    }

    public int getCapacity() {
        return capacity;
    }

    public TrainPosition getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Get the next node that this train will move to, excluding
     * the current node if this train is already at a node.
     *
     * If this train is not on a track, or there is no next node,
     * the returned Optional will be empty.
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

    public boolean move(Direction direction, double amount) {

        Preconditions.checkArgument(amount >= 0, "amount must be non-negative");
        amount = amount * direction.getMultiplier();

        TrainPosition movedPosition = position.move(amount, false)
                .orElse(null);

        if (movedPosition == null) return false;

        position = movedPosition;

        return true;
    }

    protected void setPosition(TrainPosition position) {
        this.position = position;
    }
}
