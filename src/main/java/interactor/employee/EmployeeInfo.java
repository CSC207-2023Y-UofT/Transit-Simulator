package interactor.employee;

public class EmployeeInfo {
    private final int staffNumber;
    private final String name;
    private final EmployeeType type;

    public EmployeeInfo(int staffNumber, String name, EmployeeType type) {
        this.staffNumber = staffNumber;
        this.name = name;
        this.type = type;
    }

    public int getStaffNumber() {
        return staffNumber;
    }

    public String getName() {
        return name;
    }

    public EmployeeType getType() {
        return type;
    }
}
