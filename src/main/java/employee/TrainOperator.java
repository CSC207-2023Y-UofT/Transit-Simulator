package employee;

public class TrainOperator extends Employee{


    public TrainOperator(String lastName, int num) {
        super(lastName, num);

    }

    /**
     * returns what this employee obejct is being paid
     */
    @Override
    public double getMonthlySalary() {
        return getBaseSalary();
    }

    /**
     * sets the isPaid boolean to true or false depending if this staff.TrainOperator was paid
     * @param isPaid
     */
    @Override
    public void setPaid(boolean isPaid) {
        TrainOperator.isPaid = isPaid;
    }

    /**
     * returns Line that the Employee is assigned to
     */
    public String checkLine(Employee employee) {
        return;
    }



}






