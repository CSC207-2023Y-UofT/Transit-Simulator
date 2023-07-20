import java.util.ArrayList;
import java.util.List;

public abstract class Employee {
    private String name;
    private int staffNumber = 0;

    private double baseSalary = 7000;

    private boolean isPaid = false;

    public Employee(String name, int id) {
        this.name = name;
        staffNumber = id;
    }

    /**
     * returns baseSalary of Employee objects
     */
    public static double getBaseSalary(){
        return baseSalary;
    }

    /**
     * returns what an employee object is being paid
     */
    abstract double getMonthlySalary();

    /**
     * receives payment for an employee object
     */
    abstract void setPaid();

    public abstract void setPaid(boolean isPaid);
}
x