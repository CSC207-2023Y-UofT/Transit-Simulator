package entity.employee;

import app_business.common.EmployeeType;

public class TrainOperator extends Employee {

    /**
     * Constructs a new TrainOperator object with the given employee number.
     * All employee numbers should be 3 digits long.
     *
     * @param id is an Integer
     */
    public TrainOperator(int id, String name) {
        super(id, name);
    }

    /**
     * Return the type of this employee.
     * @return EmployeeType of the employee.
     */
    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.OPERATOR;
    }

}






