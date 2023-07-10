/*  Train class
 *
 *  Current to-do list:
 *
 */









import java.util.HashMap;

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
        this.staff = new HashMap<String, ArrayList<Staff>>();

        staff.put("Driver", new ArrayList<Staff>());
        // can add more staff jobs here



    }


    /**
     *  Setter function to add staff
     *  @param type:
     *  @param obj:   TODO: rename obj with better name
     *  @return boolean whether the staff was successfully added
     */
    public boolean AddStaff (String type, Staff obj) {
        if (this.staff.containsKey(type)) {
            this.staff.get(type).add(obj);
            return true;
        } else {return false;}
    }

    // TODO: add a remover staff function

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

}

