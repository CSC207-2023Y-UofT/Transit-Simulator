// Train class
import java.util.HashMap;

class Train {
    // type params
    int capacity;
    int line;  // line number
    boolean direction;  // true for forwards, false for backwards
    HashMap<String, String> staff;



    // constructor
    public Train (int capacity, int line, boolean direction) {
        this.capacity = capacity;
        this.line = line;
        this.direction = direction;


    }

}

