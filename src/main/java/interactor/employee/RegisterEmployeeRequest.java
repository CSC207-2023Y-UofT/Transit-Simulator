package interactor.employee;

public class RegisterEmployeeRequest {
    public final String name;
    public final EmployeeType type;

    public RegisterEmployeeRequest(String name, EmployeeType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public EmployeeType getType() {
        return type;
    }
}
