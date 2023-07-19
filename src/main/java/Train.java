import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;

/*  Train class
 *
 *  Current to-do list:
 *  TODO: create a train controller to handle the logic between Train and nodes stations and tracks? Either this or make the NodeUpdater
 *  TODO: instead of occupancy, make a dictionary mapping from a passenger's desired stops to ride to hte number of passengers wanting that number
 *  TODO: create a way to keep track of emergencies, delays, scheduled maintenances.
 *  TODO: Above by doing a event calling system???? somewhere
 */


class Train {

    // constructor type params
    private final int capacity;  // max capacity
    /**
     *  Either ONLINE, MAINTENANCE or OFFLINE. Precondition: train in MAINTENANCE cannot be ONLINE
     *  Trains that are running can pass Offline trains whether at tracks or at stations. IRL justification: most
     *  tracks have spare tracks that others can pass
     */
    public enum StatusType {
        IN_SERVICE,
        MAINTENANCE,
        OUT_OF_SERVICE
    }
    StatusType status;
    private int line;  // line number
    private DirectionType direction;  // true for forwards, false for backwards. Note: Cannot be final in case of Line-type tracks
    private Map<String, List<Staff>> staff;// dict mapping from staff type to an arraylist of staff assigned to that role
    private int occupancy;  // number of passengers on board
    private Map<Integer, Integer> passengerDestinations;  // dict mapping from a passenger's desired stops to ride to the number of passengers wanting that number
    private boolean needsMaintenance = false;
    Node currentLocation;  // assuming that both Station and Track objects can be referenced by this "Node"


    // constructor
    public Train (int capacity, int line, DirectionType direction, Node currentLocation, Plot env) {  // TODO CLEAN ARCHITECTURE!!!!! CANNOT REFERENCE currentLocation because of dependency inversion!!!
        this.occupancy = 0;
        this.capacity = capacity;
        this.status = StatusType.OUT_OF_SERVICE;
        this.line = line;
        this.direction = direction;
        this.staff = new HashMap<String, ArrayList<Staff>>();

        staff.put("Driver", new ArrayList<Staff>()); // TODO rename to TrainOperator when zoey uploads
        // can add more staff jobs here

        this.currentLocation = currentLocation;
        this.passengerDestinations = new TreeMap<Integer, Integer>();  // Implements SortedMap (naturally orders the keys of the map)
    }

    /**
     *  helper method to update the passengerDestinations map when the train moves to the next station
     */
    public void updatePassengerDestinations () {
        int categories = this.passengerDestinations.size();
        if (categories > 0) {
            for (int key : this.passengerDestinations.keySet()) {  // Keys are ordered since this.passengerDestinations is a TreeMap
                this.passengerDestinations.put(key - 1, this.passengerDestinations.get(key));
                this.passengerDestinations.remove(key);
            }
        }
    }

    /**
     *  update the current location of the train and the
     *  @param node the new location of the train
     */
    public void updateCurrentLocation (Node node) {  // overloading
        this.currentLocation = node;
        this.updatePassengerDestinations();
    }

    // getters and setters

    public int getDistanceToNextStation () {
        return this.currentLocation.getDistanceToNextStation();
    }

    /**
     *  remove and return the number of passengers that want to get off at the current station
     *  @return the current location of the train
     */
    public int getOffloadingPassengers () {
        int offloadingPassengers = this.passengerDestinations.getOrDefault(0, 0);
        this.passengerDestinations.remove(0);  // implicit .containsKey(0) check
        return offloadingPassengers;
    }

    /**
     *
     *  @param numPassengers number of passengers to add to the train
     *  @param stationsToRide number of stations the passengers will ride before getting off
     *  @return true iff the passengers were successfully added to the train
     */
    public boolean addPassengers (int numPassengers, int stationsToRide) {
        if (this.occupancy + numPassengers > this.capacity) {
            return false;
        }
        this.occupancy += numPassengers;
        this.passengerDestinations.put(stationsToRide, this.passengerDestinations.getOrDefault(stationsToRide, 0) + numPassengers);
        return true;
    }

    public void scheduleInService () {
        this.status = StatusType.IN_SERVICE;
    }

    public void scheduleOutOfService () {
        this.status = StatusType.OUT_OF_SERVICE;
    }

    public boolean getNeedsMaintenance () {
        return this.needsMaintenance;
    }

    public void setNeedsMaintenance (boolean needsMaintenance) {
        this.needsMaintenance = needsMaintenance;
        if (needsMaintenance) {
            this.status = StatusType.MAINTENANCE;
        } else {
            this.status = StatusType.IN_SERVICE;
        }
    }

    /**
     *  Setter function that updates this train's location based on the current location, direction and line
     */
    public void updateCurrentLocation () {
        this.currentLocation = this.getNextStation();
    }

    /**
     *  Setter function for changing the direction of this train.
     */
    public void changeDirection () { // TODO: unused function
        if (this.direction == DirectionType.FORWARD) {  // the == here is nullsafe
            this.direction = DirectionType.BACKWARD;
        } else {
            this.direction = DirectionType.FORWARD;  // By representation invariance, this.direction must be the other
        }
    }

    /**
     *  Setter function for the direction of this train.
     *  @param direction DirectionType.FORWARD iff train is going in the forward direction, DirectionType.BACKWARD when going backwards
     */
    public void setDirection (DirectionType direction) {
        this.direction = direction;
    }

    /**
     *  Query function for the direction of this train.
     *  @return DirectionType.FORWARD iff train is going in the forward direction, DirectionType.BACKWARD when going backwards
     */
    public DirectionType getDirection () {
        return this.direction;
    }

    /**
     *  Calculator function for the next station based on the current location. The next station should be returned no
     *  matter if the current location is the 'previous' station or a track in between.
     *  @return the next Station
     */
    public Station getNextStation () {
        return env.getNextStation(this.line, this.direction, this.currentLocation);
    }


    /**
     *  Setter function to add staff
     *  @param position staff type to add employee to
     *  @param employee Staff obj to add
     *  @return boolean whether the staff was successfully added
     */
    public boolean addStaff (String position, Staff employee) {
        if (this.staff.containsKey(position)) {
            this.staff.get(position).add(employee);
            return true;
        } else {return false;}
    }

    /**
     *  Query function for staff currently working in a specific position
     *  @param position String position to check for the presence of a worker
     *  @return Arraylist of staff currently in that position, or null if the position was not found
     */
    public List<Staff> getStaffInJob (String position) {
        return this.staff.getOrDefault(position, null);
    }

    /**
     *  Setter function to remove staff
     *  @return boolean iff the employee was successfully removed
     */
    public boolean removeStaff (String position, Staff employee) {
        if (this.staff.containsKey(position) && this.staff.get(position).contains(employee)) {  // using short circuit eval
            this.staff.get(position).remove(employee);
            return true;
        } else {return false;}
    }

    /**
     *  Query function for the line number this train is running on.
     *  @return the line number
     */
    public int getLine () {
        return this.line;
    }

    /**
     *  Setter function for this train's line number
     *  @param line int line number
     */
    public void setLine (int line) {
        this.line = line;
    }

    /**
     *  Query function for this train's status
     *  @return this train's status
     */
    public StatusType getStatus () {
        return this.status;
    }

    /**
     *  setter function for this train's status
     *  @param status must be a StatusType enum
     */
    public void setStatus (StatusType status) {
        this.status = status;
    }

    /**
     * Setter function to transfer passengers to this train
     * @param passengers integer to put on the train
     * @return number of passengers not able to board the train
     */
    public int addOccupancy (int passengers) {
        if (this.occupancy + passengers > this.capacity) {
            passengers = this.occupancy + passengers - this.capacity;
            this.occupancy = this.capacity;
            return passengers;
        } else {
            this.occupancy += passengers;
            return 0;
        }
    }

    /**
     *  Query function for this train's current occupancy
     *  @return the occupancy
     */
    public int getOccupancy () {
        return this.occupancy;
    }

    /**
     *  Query function for this train's capacity
     *  @return the capacity
     */
    public int getCapacity () {
        return this.capacity;
    }
}

