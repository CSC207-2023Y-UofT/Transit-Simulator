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
     * Either IN_SERVICE, SCHEDULED_MAINTENANCE, UNDER_MAINTENANCE or OUT_OF_SERVICE.
     * <br>

     * IN_SERVICE:            Running properly.
     * SCHEDULED_MAINTENANCE: still running, will stop for maintenance at the next station. (not automatically under maintenance)
     * UNDER_MAINTENANCE:     stopped for maintenance at a station and staff assigned to maintain.
     * OUT_OF_SERVICE:        Not running.
     * <br>

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

    /** The length of a train. */
    public static int LENGTH = 100;
    /** The maximum speed of a train in m/s. This is around 115km/h. */
    public static int MAX_SPEED = 32;
    /** The time that a train will wait at a station in milliseconds; 20000 milliseconds = 20 seconds. */
    public static long STATION_WAIT_TIME = 1000 * 20;

    /** The transit tracker (the controller) that this train is associated with. */
    private final TransitTracker transitTracker;
    /** The list of passengers on this train. */
    private final List<Passenger> passengerList = new ArrayList<>();
    /** The capacity of this train. */
    private final int capacity;
    /** The status of this train. */
    private Status status = Status.OUT_OF_SERVICE;
    /** The position of this train. */
    private TrainPosition position;
    /**
     * The staff assigned to this train.
     * HashMap mapping TrainJobs to Employees.
     */
    private final Map<TrainJob, Employee> staff = new HashMap<>();

    /**
     *  Creates a train on the given track in the given direction with the given capacity.
     *  @param transitTracker the transit tracker that this train is associated with
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
     *  Get the employee assigned to a job on this train.
     *  @param job the job to get the employee for
     */
    public Employee getStaff(TrainJob job) {  // This function is overloading for .getStaff()
        return staff.get(job);
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
     * Remove and return an employee from a job on this train.
     * @param job the job to remove the employee from
     * @return the employee if it was currently assigned to the TrainJob job
     */
    public Employee removeStaff(TrainJob job) {
        return this.staff.remove(job);
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
     * Handle the alighting passengers on this train.
     * @return the list of passengers that alighted
     */
    public List<Passenger> handleAlightingPassengers() {
        List<Passenger> alightingPassengers = new ArrayList<>();
        Iterator<Passenger> passengerIterator = passengerList.iterator();
        while (passengerIterator.hasNext()) {
            Passenger passenger = passengerIterator.next();
            if (passenger.willAlight()) {
                alightingPassengers.add(passenger);
                passengerIterator.remove();  // This is a safe way to remove elements while iterating over a collection
                                             // without a risk of a ConcurrentModificationException.
            }
        }

        return alightingPassengers;
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

     * If this train is not on a track, or there is no next node,
     * the returned Optional will be empty.
     *
     * @param direction the direction to look for the next node
     * @return the next node, or empty if there is no next node
     */
    public Optional<Node> getNextNode(Direction direction) {  //TODO: REFLECT DIRECTION BEHAVIOR CHANGES
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
    public Optional<Double> getDistanceToNextNode(Direction direction) { //TODO: REFLECT DIRECTION BEHAVIOR CHANGES

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

        // Check for arrival at a NodeTrackSegment
        TrackSegment currTrack = position.getTrack();
        if (currTrack != movedPosition.getTrack() && currTrack instanceof NodeTrackSegment) { // HELP: instanceof used
            decrementAllPassengersStationsToTravel();
        }

        // Then move the train
        position = movedPosition;

        return true;
    }

    /**
     *  Decrement the stations to travel for all passengers on this train.
     */
    private void decrementAllPassengersStationsToTravel() {
        for (Passenger passenger : this.passengerList) {
            passenger.decrementStationsToTravel();
        }
    }
}
