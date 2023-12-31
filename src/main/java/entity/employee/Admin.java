package entity.employee;

import app_business.common.EmployeeType;

/**
 * The Admin class extends the Employee class.
 * It represents an administrative employee who has the authority to manage, pay, and assign other employees to lines.
 * The Admin employee earns four times the base monthly salary of a standard Employee.
 */
public class Admin extends Employee {

    /**
     * Constructs a new Admin object with the given employee number.
     * all employee numbers should be 3 digits long.
     *
     * @param num The unique employee number.
     */
    public Admin(int num, String name) {
        super(num, name);
    }

    /**
     * Returns the monthly salary of this Admin, which is four times the base salary.
     *
     * @return The monthly salary of this Admin.
     */
    @Override
    public double getMonthlySalary() {
        return 4 * super.getMonthlySalary();
    }

    /**
     * Returns the type of this Admin.
     *
     * @return The type of this Admin
     */
    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.ADMINISTRATOR;
    }

}