package entity.employee;

import app_business.common.EmployeeType;

/**
 * The TrainEngineer class extends the Employee class.
 * It represents a train engineer who can fix trains and has a monthly salary 1.2 times the base salary of an Employee.
 */
public class TrainEngineer extends Employee {

    /**
     * Constructs a new TrainEngineer object with the given employee number.
     * All employee numbers should be 3 digits long.
     *
     * @param id is an Integer
     */
    public TrainEngineer(int id, String name) {
        super(id, name);
    }

    /**
     * Returns the monthly salary of this TrainEngineer, which is 1.2 times the base salary.
     *
     * @return The monthly salary of this TrainEngineer.
     */
    @Override
    public double getMonthlySalary() {
        return 1.2 * super.getMonthlySalary();
    }

    /**
     * Returns the type of this employee.
     *
     * @return The type of this employee.
     */
    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.ENGINEER;
    }

}
