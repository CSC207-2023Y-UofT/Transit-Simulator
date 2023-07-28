package employee;

public abstract class Employee {
    private static final double BASE_SALARY = 7000;
    private final int staffNumber;
    private final String lastName;

    public Employee(String name, int id) {
        this.lastName = name;
        this.staffNumber = id;
    }

    public int getStaffNumber() {
        return staffNumber;
    }

    /**
     * returns what an employee object is being paid
     */
    public double getBaseSalary() {
        return BASE_SALARY;
    }
}