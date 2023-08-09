package interactor.employee;

/**
 * Input data request model for the register employee use case.
 */
public class RegisterEmployeeRequest {

    /**
     * The type of the employee
     */
    public final EmployeeType type;

    /**
     * Create a new RegisterEmployeeRequest object
     *
     * @param type The type of the employee
     */
    public RegisterEmployeeRequest(EmployeeType type) {
        this.type = type;
    }

    /**
     * Get the type of the employee
     *
     * @return The type of the employee
     */
    public EmployeeType getType() {
        return type;
    }
}
