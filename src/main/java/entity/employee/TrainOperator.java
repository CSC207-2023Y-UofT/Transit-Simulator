package entity.employee;

import app_business.employee.EmployeeType;

public class TrainOperator extends Employee {

    /**
     * Constructs a new TrainOperator object with the given employee number.
     * all employee numbers should be 3 digits long.
     *
     * @param id is an Integer
     */
    public TrainOperator(int id, String name) {
        super(id, name);
    }

    /**
     * returns what this employee object is being paid
     *
     * @return a double
     */
    @Override
    public double getMonthlySalary() {
        return super.getMonthlySalary();
    }

    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.OPERATOR;
    }

}






