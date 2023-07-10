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
    int capacity;  // max capacity
    /**
     *  Either "Online", "Maintenance" or "Offline". Precondition: train in "Maintenance" cannot be online
     *  Trains that are running can pass Offline trains whether at tracks or at stations. IRL justification: most
     *  tracks have spare tracks that others can pass
     */
    private final HashSet<String> STATUS_TYPES = new HashSet<> (Arrays.asList("Online", "Maintenance", "Offline"));
    String status;
    int line;  // line number
    boolean direction;  // true for forwards, false for backwards
    // dict mapping from staff type to an arraylist of staff assigned to that role
    private HashMap<String, ArrayList<Staff>> staff;
    private int occupancy;

    // Debatable information can be removed if needed:
    Node currentLocation;  // assuming that both Station and Track objects can be referenced by this
    Station nextStation;


    // constructor
    public Train (int capacity, int line, boolean direction, Node currentLocation, Map env) {
        this.occupancy = 0;
        this.capacity = capacity;
        this.status = "Offline";
        this.line = line;
        this.direction = direction;
        this.staff = new HashMap<>();

        staff.put("Driver", new ArrayList<Staff>());
        // can add more staff jobs here


        // Debatable information whether needed or not:
        this.currentLocation = currentLocation;
        this.nextStation = env.getNextStation(this.line, this.currentLocation);
    }


    /**
     *  Setter function to add staff
     *  @param position:
     *  @param employee:
     *  @return boolean whether the staff was successfully added
     */
    public boolean AddStaff (String position, Staff employee) {
        if (this.staff.containsKey(position)) {
            this.staff.get(position).add(employee);
            return true;
        } else {return false;}
    }

    /**
     *  Query function for staff currently working in a specific position
     *  @param position: String position to check for the presence of a worker
     *  @return Arraylist of staff currently in that position, or null if the position was not found
     */
    public ArrayList<Staff> GetStaffInJob (String position) {
        return this.staff.getOrDefault(position, null);
    }

    /** Setter function to remove staff
     *
     *
     * @return boolean iff the employee was successfully removed
     */
    public boolean RemoveStaff (String position, Staff employee) {
        if (this.staff.containsKey(position) && this.staff.get(position).contains(employee)) {  // using short circuit eval
            this.staff.get(position).remove(employee);
            return true;
        } else {return false;}
    }

    /**
     *  Query function for the direction of this train.
     *  @return true iff train is going in the forward direction
     */
    public boolean GetDirection () {
        return this.direction;
    }

    /**
     *  Query function for the line number this train is running on.
     *  @return the line number
     */
    public int GetLine () {
        return this.line;
    }

    /**
     *  Setter function for this train's line number
     *  @param line: int line number
     */
    public void SetLine (int line) {
        this.line = line;
    }

    public String GetStatus () {
        return this.status;
    }

    /**
     *  setter function for this train's status
     *  @param status;
     *  @return boolean iff train's status was valid and successfully changed
     */
    public boolean SetStatus (String status) {
        if (STATUS_TYPES.contains(status)) {
            this.status = status;
            return true;
        } else {return false;}
    }

    /**
     * Setter function to transfer passengers to this train
     * @param passengers: integer to put on the train
     * @return number of passengers not able to board the train
     */
    public int AddOccupancy (int passengers) {
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

