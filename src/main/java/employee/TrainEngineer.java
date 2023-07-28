package employee;

import model.train.Train;

import java.util.ArrayList;
import java.util.List;

public class TrainEngineer extends Employee{

    private List<Employee> employees = new ArrayList<>();

    public TrainEngineer(String lastName, int num) {
        super(lastName, num);
    }

    /**
     * returns what this employee obejct is being paid
     */
    @Override
    public double getMonthlySalary() {
        return 1.2 * super.getBaseSalary();
    }

    /**
     * receives payment for an employee object
     */
    @Override
    public void setPaid(boolean isPaid) {
        TrainEngineer.isPaid = isPaid; //when this object is paid, how is this information retained or updated?
    }

    /**
     * fixes Train object's maintenance issues
     */
    public void fixMaintenanceIssue(Train train) {
        if (train.getNeedsMaintenance() == true) {
            train.setNeedsMaintenance(false);
        }
    }

}
