package train.old;

import java.util.ArrayList;
import java.util.List;

public abstract class Employee {
    private static final double BASE_SALARY = 7000;
    private final int staffNumber;
    private final String name;

    public Employee(String name, int id) {
        this.name = name;
        this.staffNumber = id;
    }

    public int getStaffNumber() {
        return staffNumber;
    }

    /**
     * returns what an employee object is being paid
     */
    public double getMonthlySalary() {
        return BASE_SALARY;
    }
}