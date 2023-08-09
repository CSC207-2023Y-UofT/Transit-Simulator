package interactor.employee;

import java.util.Optional;

/**
 * Output data class for the employee interactor
 */
public class EmployeeDTO {
    /**
     * The staff number of the employee
     */
    private final int staffNumber;
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
     * @param type        The type of the employee
     * @param assignment  The assignment of the employee, if any
     */
    public EmployeeDTO(int staffNumber, EmployeeType type, EmployeeAssignment assignment) {
        this.staffNumber = staffNumber;
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