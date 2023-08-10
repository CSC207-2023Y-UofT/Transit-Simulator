package entity.employee;

import app_business.common.EmployeeAssignment;
import app_business.common.EmployeeType;

import java.io.Serializable;
import java.util.Optional;

/**
 * The Employee class is an abstract class that represents an employee.
 * Each employee has a unique staff number and a payment status.
 * The base monthly salary for an employee is specified as a static final field (BASE_SALARY).
 * The concrete classes that extend Employee should provide the implementation for setPaid and getPaid methods.
 */
public abstract class Employee implements Serializable {

    /**
     * The base salary for all employees.
     */
    private static final double BASE_SALARY = 7000;

    /**
     * The unique staff number for this employee.
     */
    private final int staffNumber;

    private final String name;

    /**
     * The payment status for this employee. True if the employee has been paid; otherwise false.
     */
    private boolean paid = false;

    /**
     * The assignment of this employee.
     */
    private EmployeeAssignment trainAssignment = null;

    /**
     * Constructs a new Employee object with the given staff number.
     * all employee numbers should be 3 digits long.
     *
     * @param id   The unique staff number.
     * @param name
     */
    public Employee(int id, String name) {
        this.staffNumber = id;
        this.name = name;
    }

    /**
     * Sets the payment status of this employee.
     *
     * @param isPaid The new payment status. True if the employee has been paid; otherwise false.
     */
    public void setPaid(boolean isPaid) {
        this.paid = isPaid;
    }

    /**
     * Returns the payment status of this employee.
     *
     * @return True if the employee has been paid; otherwise false.
     */
    public boolean isPaid() {
        return paid;
    }

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

    /**
     * Returns the name of this employee.
     */
    public String getName() {
        return name;
    }

    public abstract EmployeeType getEmployeeType();

    /**
     * Returns the assignment of this employee.
     *
     * @return The assignment of this employee.
     */
    public Optional<EmployeeAssignment> getAssignment() {
        return Optional.ofNullable(trainAssignment);
    }

    /**
     * Sets the assignment of this employee.
     *
     * @param trainAssignment The new assignment of this employee.
     */
    public void setAssignment(EmployeeAssignment trainAssignment) {
        this.trainAssignment = trainAssignment;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Employee)) return false;
        return ((Employee) obj).staffNumber == this.staffNumber;
    }
}
