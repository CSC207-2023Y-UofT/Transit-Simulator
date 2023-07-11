/*  Train class
 *
 *  Current to-do list:
 *
 */



import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

class Train {

    // constructor type params
    private final int capacity;  // max capacity
    /**
     *  Either ONLINE, MAINTENANCE or OFFLINE. Precondition: train in MAINTENANCE cannot be ONLINE
     *  Trains that are running can pass Offline trains whether at tracks or at stations. IRL justification: most
     *  tracks have spare tracks that others can pass
     */
    public enum StatusType {
        ONLINE,
        MAINTENANCE,
        OFFLINE
    }
    StatusType status;
    int line;  // line number
    boolean direction;  // true for forwards, false for backwards
    // dict mapping from staff type to an arraylist of staff assigned to that role
    private Map<String, List<Staff>> staff;
    private int occupancy;
    Node currentLocation;  // assuming that both Station and Track objects can be referenced by this "Node"


    // constructor
    public Train (int capacity, int line, boolean direction, Node currentLocation, Map env) {
        this.occupancy = 0;
        this.capacity = capacity;
        this.status = StatusType.OFFLINE;
        this.line = line;
        this.direction = direction;
        this.staff = new HashMap<String, ArrayList<Staff>>();

        staff.put("Driver", new ArrayList<Staff>());
        // can add more staff jobs here

        this.currentLocation = currentLocation;
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
    public ArrayList<Staff> getStaffInJob (String position) {
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
     *  Query function for the direction of this train.
     *  @return true iff train is going in the forward direction
     */
    public boolean getDirection () {
        return this.direction;
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

