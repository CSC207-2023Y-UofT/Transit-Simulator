package employee;

import employee.Employee;
import interactor.employee.EmployeeType;

public class TrainOperator extends Employee {

    /**
     * Constructs a new TrainOperator object with the given employee number.
     * @param id is an Integer
     */
    public TrainOperator(int id) {
        super(id);
    }

    /**
     * returns what this employee object is being paid
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

    /**
     * sets the isPaid boolean to true or false depending on if this TrainOperator was paid
     * @param isPaid is a boolean
     */
    @Override
    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    /**
     * returns whether this TrainOperator object has been paid yet
     * @return a boolean
     */
    @Override
    public boolean getPaid() {
        return this.isPaid;
    }

}






