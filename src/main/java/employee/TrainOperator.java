package employee;

public class TrainOperator extends Employee{

    private boolean isPaid = false;


    public TrainOperator(String lastName, int num) {
        super(lastName, num);

    }

    /**
     * returns what this employee object is being paid
     */
    @Override
    public double getMonthlySalary() {
        return super.getMonthlySalary();
    }

    /**
     * sets the isPaid boolean to true or false depending on if this TrainOperator was paid
     * @param isPaid
     */
    @Override
    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    /**
     * returns Line that the current instance of TrainOperator is assigned to, must ask an admin
     * @param admin
     */
    public Integer checkTrainOperatorLine(Admin admin) {
        return admin.checkLine(this);
    }



}






