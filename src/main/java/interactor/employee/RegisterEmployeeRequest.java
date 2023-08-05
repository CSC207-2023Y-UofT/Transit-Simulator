package interactor.employee;

public class RegisterEmployeeRequest {
    public final EmployeeType type;

    public RegisterEmployeeRequest(EmployeeType type) {
        this.type = type;
    }


    public EmployeeType getType() {
        return type;
    }
}
