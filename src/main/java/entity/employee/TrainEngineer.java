package entity.employee;

import app_business.common.EmployeeType;
import entity.model.train.TrainStatus;
import entity.model.train.Train;
import stats.entry.impl.expense.MaintenanceStat;
import stats.StatDataController;

import java.util.Random;

/**
 * The TrainEngineer class extends the Employee class.
 * It represents a train engineer who can fix trains and has a monthly salary 1.2 times the base salary of an Employee.
 */
public class TrainEngineer extends Employee {

    /**
     * Constructs a new TrainEngineer object with the given employee number.
     *
     * @param num The unique employee number.
     */
    public TrainEngineer(int num, String name) {
        super(num, name);
    }

    /**
     * Returns the monthly salary of this TrainEngineer, which is 1.2 times the base salary.
     *
     * @return The monthly salary of this TrainEngineer.
     */
    @Override
    public double getMonthlySalary() {
        return 1.2 * super.getMonthlySalary();
    }

    /**
     * Returns the type of this employee.
     *
     * @return The type of this employee.
     */
    @Override
    public EmployeeType getEmployeeType() {
        return EmployeeType.ENGINEER;
    }

    /**
     * Fixes the specified Train's maintenance issues.
     * The method sets the status of the Train to OUT_OF_SERVICE and generates a random cost for the maintenance.
     *
     * @param train The Train that needs to be fixed.
     */
    public void fixTrain(Train train, StatDataController controller) {
        train.setStatus(TrainStatus.OUT_OF_SERVICE);
        Random rand = new Random();
        int cost = rand.nextInt(5000 + 1) + 5000;  // Generates a random number between 5000 and 10000
        controller.record(new MaintenanceStat(cost));
    }

}
