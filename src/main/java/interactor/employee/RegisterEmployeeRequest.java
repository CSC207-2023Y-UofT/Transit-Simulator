package interactor.employee;

/**
 * Input data request model for the register employee use case.
 */
public class RegisterEmployeeRequest {

    /**
     * The name of the employee
     */
    public final String name;

    /**
     * The type of the employee
     */
    public final EmployeeType type;

    /**
     * Create a new RegisterEmployeeRequest object
     * @param name The name of the employee
     * @param type The type of the employee
     */
    public RegisterEmployeeRequest(String name, EmployeeType type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Get the name of the employee
     * @return The name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * Get the type of the employee
     * @return The type of the employee
     */
    public EmployeeType getType() {
        return type;
    }
}
