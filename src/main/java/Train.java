// Train class
import java.util.HashMap;
import java.util.ArrayList;

class Train {
    // constructor type params
    int capacity;
    int line;  // line number
    boolean direction;  // true for forwards, false for backwards
    private HashMap<String, ArrayList<Staff>> staff;  // dict mapping from staff type to an arraylist of staff assigned to that role


    //

    private int occupancy = 0;



    // constructor
    public Train (int capacity, int line, boolean direction) {
        this.capacity = capacity;
        this.line = line;
        this.direction = direction;
        this.staff = new HashMap<>();

        staff.put("Driver", new ArrayList<Staff>());
        // we can add more staff jobs here



    }


    /*
     *  Setter function to add staff
     *  @param String type:
     *  @param Staff obj:   TODO: rename obj with better name
     */
    public void AddStaff (String type, Staff obj) {
        staff.get(type).add(obj);
    }

    // TODO: add a remover staff function

    /*  Query function for the direction of this train.
     *  @return true iff train is going in the forward direction
     */
    public boolean GetDirection () {
        return this.direction;
    }

    /*  Query function for the line number this train is running on.
     *  @return the line number
     */
    public int GetLine () {
        return this.line;
    }

}

