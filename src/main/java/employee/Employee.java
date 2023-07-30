package employee;

public abstract class Employee {
    private static final double BASE_SALARY = 7000;
    private final int staffNumber;

    protected boolean isPaid = false;

    public Employee(int id) {
        this.staffNumber = id;
    }

    /**
     * sets the isPaid boolean to true or false depending on if this employee was paid
     * @param isPaid is a boolean
     */
    abstract void setPaid(boolean isPaid);

    /**
     * returns whether this employee object has been paid yet
     * @return a boolean
     */
    abstract boolean getPaid();

    /**
     * returns what an employee object is being paid
     * @return a double
     */
    public double getMonthlySalary() {
        return BASE_SALARY;
    }
}