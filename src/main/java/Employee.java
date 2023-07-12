import java.util.ArrayList;
import java.util.List;

public abstract class Employee {
    private String name;
    private int staffNumber = 0;

    public Employee(String name, int id) {
        this.name = name;
        staffNumber = id;
    }

    /**
     * returns what an employee object is being paid
     */
    abstract double getMonthlySalary();



}
