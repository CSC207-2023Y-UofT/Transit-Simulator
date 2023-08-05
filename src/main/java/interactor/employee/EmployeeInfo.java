package interactor.employee;

public class EmployeeInfo {
    private final int staffNumber;
    private final EmployeeType type;

    public EmployeeInfo(int staffNumber, EmployeeType type) {
        this.staffNumber = staffNumber;
        this.type = type;
    }

    public int getStaffNumber() {
        return staffNumber;
    }

    public EmployeeType getType() {
        return type;
    }
}
