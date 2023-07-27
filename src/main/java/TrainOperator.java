import java.util.HashMap;

public class TrainOperator extends Employee{


    public TrainOperator(String name, int num) {
        super(name, num);

    }

    /**
     * returns what this employee obejct is being paid
     */
    @Override
    public double getMonthlySalary() {
        return super.getBaseSalary();
    }

    /**
     * receives payment for an employee object
     */
    @Override
    public void setPaid(boolean isPaid) {
        TrainOperator.isPaid = isPaid;
    }

    /**
     * returns Line that the Employee is assigned to
     */
    public String checkLine(Employee employee) {
        return
    }



}






