package model.train;

import model.*;
import model.control.TransitTracker;
import model.staff.Employee;
import model.node.Node;
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

    private final TransitTracker transitTracker;
    private final List<Passenger> passengerList = new ArrayList<>();
    private final int capacity;
    private Status status = Status.OUT_OF_SERVICE;
    private Direction direction;
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
     * Get the next node that this train will move to
     */
    public Node getNextNode() {
        return direction == Direction.FORWARD ?
                position.getNode().getNext() :
                position.getNode().getPrev();
    }

    public double getDistanceToNextNode() {
        return direction == Direction.FORWARD ?
                position.getNode().getLength() - position.getPositionOnNode() :
                position.getPositionOnNode();
    }

    public boolean move(Direction direction, double amount) {
        Preconditions.checkArgument(amount >= 0, "amount must be non-negative");
        amount = amount * direction.getMultiplier();

        Preconditions.checkArgument(amount >= 0, "amount must be non-negative");

        Node node = position.getNode();
        double target = position.getPositionOnNode() + amount;

        // While the target is not within the bounds of
        // the current node, change which node we are on
        while (!(target >= 0) || !(target < node.getLength())) {

            Node nextNode = node.getNextNode(direction);
            if (nextNode == null) {
                return false; // Endpoint
            }

            double nextNodeOffset = direction == Direction.FORWARD ?
                    node.getLength() - position.getPositionOnNode() :
                    -position.getPositionOnNode();

            target -= nextNodeOffset;

            node = nextNode;

        }

        position = new TrainPosition(node, target);

        return true;
    }

    protected void setPosition(TrainPosition position) {
        this.position = position;
    }
}
