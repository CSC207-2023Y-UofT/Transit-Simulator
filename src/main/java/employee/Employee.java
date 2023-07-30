package employee;

public abstract class Employee {
    private static final double BASE_SALARY = 7000;
    private final int staffNumber;
    private final String lastName;

    private boolean isPaid = false;

    public Employee(String name, int id) {
        this.lastName = name;
        this.staffNumber = id;
    }

    /**
     * sets the isPaid boolean to true or false depending on if this employee was paid
     * @param isPaid
     */
    abstract void setPaid(boolean isPaid);

    /**
     * returns the staffNumber of the employee
     */
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