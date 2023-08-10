package app_business.employee;

/**
 * Input data request model for the register employee use case.
 */
public class RegisterEmployeeRequest {

    /**
     * The type of the employee
     */
    public final EmployeeType type;

    /**
     * The name of the employee
     */
    public final String name;

    /**
     * Create a new RegisterEmployeeRequest object
     *
     * @param type The type of the employee
     * @param name
     */
    public RegisterEmployeeRequest(EmployeeType type, String name) {
        this.type = type;
        this.name = name;
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
     * Get the name of the employee
     */
    public String getName() {
        return name;
    }
}
