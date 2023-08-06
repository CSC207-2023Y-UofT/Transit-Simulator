package employee;

import interactor.employee.EmployeeAssignment;
import interactor.employee.EmployeeType;

import java.util.Optional;

/**
 * The Employee class is an abstract class that represents an employee.
 * Each employee has a unique staff number and a payment status.
 * The base monthly salary for an employee is specified as a static final field (BASE_SALARY).
 * The concrete classes that extend Employee should provide the implementation for setPaid and getPaid methods.
 */
public abstract class Employee {

    /**
     * The base salary for all employees.
     */
    private static final double BASE_SALARY = 7000;

    /**
     * The unique staff number for this employee.
     */
    private final int staffNumber;

    /**
     * The payment status for this employee. True if the employee has been paid; otherwise false.
     */
    protected boolean isPaid = false;

    private EmployeeAssignment trainAssignment = null;

    /**
     * Constructs a new Employee object with the given staff number.
     * all employee numbers should be 3 digits long.
     * @param id The unique staff number.
     */
    public Employee(int id) {
        this.staffNumber = id;
    }

    /**
     * Sets the payment status of this employee.
     *
     * @param isPaid The new payment status. True if the employee has been paid; otherwise false.
     */
    abstract void setPaid(boolean isPaid);

    /**
     * Returns the payment status of this employee.
     *
     * @return True if the employee has been paid; otherwise false.
     */
    abstract boolean getPaymentStatus();

    /**
     * Returns the monthly salary of this employee, which is the base salary for all employees.
     *
     * @return The monthly salary of this employee.
     */
    public double getMonthlySalary() {
        return BASE_SALARY;
    }

    /**
     * Returns the staff number of this employee.
     *
     * @return The staff number of this employee.
     */
    public int getStaffNumber() {
        return staffNumber;
    }

    public abstract EmployeeType getEmployeeType();

    /**
     * Returns the assignment of this employee.
     * @return
     */
    public Optional<EmployeeAssignment> getAssignment() {
        return Optional.ofNullable(trainAssignment);
    }

    public void setAssignment(EmployeeAssignment trainAssignment) {
        this.trainAssignment = trainAssignment;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Employee)) return false;
        return ((Employee) obj).staffNumber == this.staffNumber;
    }
}
