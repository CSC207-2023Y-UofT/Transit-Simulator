package train;

import train.old.Employee;

public class TrainEngineer extends Employee  {

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
        if (train.getStatus() == Train.Status.UNDER_MAINTENANCE) {
            train.setStatus(Train.Status.OUT_OF_SERVICE);
        }
    }

    /**
     * add the employee object to the specified line
     * @param employee
     * @param line
     */
    public void addEmployeeToLine(String line, Employee employee){
        lineToStaff.put(line, employee);
    }
}
