package model.train;

import model.staff.Employee;
import model.train.Train;

public class TrainEngineer extends Employee {

    public TrainEngineer(String name, int num) {
        super(name, num);
    }

    /**
     * returns what this employee obejct is being paid
     */
    @Override
    public double getMonthlySalary() {
        return 1.2 * super.getMonthlySalary();
    }

    /**
     * fixes Train object's maintenance issues
     */
    public void fixTrain(Train train) {
        train.setStatus(Train.Status.OUT_OF_SERVICE);
    }

}
