package interactor.employee;

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
     * Create a new EmployeeInfo object
     *
     * @param staffNumber The staff number of the employee
     * @param name        The name of the employee
     * @param type        The type of the employee
     */
    public EmployeeInfo(int staffNumber, String name, EmployeeType type) {
        this.staffNumber = staffNumber;
        this.name = name;
        this.type = type;
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
}
