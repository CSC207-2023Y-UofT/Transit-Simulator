package interactor.employee;

import model.train.TrainRole;

import java.util.Optional;

/**
 * Output data class for the employee interactor
 */
public class EmployeeInfo {
    /**
     * The staff number of the employee
     */
    private final int staffNumber;
    /**
     * The name of the employee
     */
    private final String name;
    /**
     * The type of the employee
     */
    private final EmployeeType type;

    /**
     * The assignment of the employee, if any
     */
    private final EmployeeAssignment assignment;

    /**
     * Create a new EmployeeInfo object
     *
     * @param staffNumber The staff number of the employee
     * @param name        The name of the employee
     * @param type        The type of the employee
     * @param job         The job of the employee
     * @param trainName   The name of the train they are working on
     * @param assignment
     */
    public EmployeeInfo(int staffNumber, String name, EmployeeType type, TrainRole job, String trainName, EmployeeAssignment assignment) {
        this.staffNumber = staffNumber;
        this.name = name;
        this.type = type;
        this.assignment = assignment;
    }

    /**
     * Get the staff number of the employee
     */
    public int getStaffNumber() {
        return staffNumber;
    }

    /**
     * Get the name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * Get the type of the employee
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * Get the assignment of the employee, if any
     */
    public Optional<EmployeeAssignment> getAssignment() {
        return Optional.ofNullable(assignment);
    }
}
