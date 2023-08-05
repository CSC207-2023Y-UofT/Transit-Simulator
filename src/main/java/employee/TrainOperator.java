package employee;

import employee.Employee;
import interactor.employee.EmployeeType;

public class TrainOperator extends Employee{


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

    /**
     * returns Line that the current instance of TrainOperator is assigned to, must ask an admin
     * @param admin is an Admin
     * @return an Integer
     */
    public Integer checkTrainOperatorLine(Admin admin) {
        return admin.checkLine(this);
    }



}






