package model.train;

import employee.Employee;

public class TrainEngineer extends Employee {

    public TrainEngineer(String name, int num) {
        super(name, num);
    }

    /**
     * returns what this employee obejct is being paid
     */
    @Override
    public double getBaseSalary() {
        return 1.2 * super.getBaseSalary();
    }

    /**
     * fixes Train object's maintenance issues
     */
    public void fixTrain(Train train) {
        train.setStatus(Train.Status.OUT_OF_SERVICE);
    }

}
