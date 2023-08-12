package app_business.dto;

import app_business.common.EmployeeAssignment;
import app_business.common.EmployeeType;

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
     * What do you think this is?
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
     * @param assignment  The assignment of the employee, if any
     */
    public EmployeeDTO(int staffNumber, String name, EmployeeType type, EmployeeAssignment assignment) {
        this.staffNumber = staffNumber;
        this.name = name;
        this.type = type;
        this.assignment = assignment;
    }

    /**
     * Get the staff number of the employee
     *
     * @return The staff number of the employee
     */
    public int getStaffNumber() {
        return staffNumber;
    }

    /**
     * Get the name of the employee
     *
     * @return The name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * Get the type of the employee
     *
     * @return The type of the employee
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * Get the assignment of the employee, if any
     *
     * @return The assignment of the employee, if any
     */
    public Optional<EmployeeAssignment> getAssignment() {
        return Optional.ofNullable(assignment);
    }

}
