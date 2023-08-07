package employee;

import employee.Employee;
import interactor.employee.EmployeeType;

public class TrainOperator extends Employee {

    /**
     * Constructs a new TrainOperator object with the given employee number.
     * all employee numbers should be 3 digits long.
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

    /**
     * returns the type of employee this is
     * @return operator type
     */
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
    public boolean getPaymentStatus() {
        return this.isPaid;
    }

}






