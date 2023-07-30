package employee;

import model.train.Train;
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
    public TrainEngineer(int num) {
        super(num);
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
     * Sets the payment status of this TrainEngineer.
     *
     * @param isPaid The new payment status. True if the TrainEngineer has been paid; otherwise false.
     */
    @Override
    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    /**
     * Returns the payment status of this TrainEngineer.
     *
     * @return True if the TrainEngineer has been paid; otherwise false.
     */
    @Override
    public boolean getPaid() {
        return this.isPaid;
    }

    /**
     * Returns the line number that this TrainEngineer is assigned to.
     * This method requires an Admin instance to check the line assignment.
     *
     * @param admin The Admin instance that checks the line assignment.
     * @return The line number that this TrainEngineer is assigned to.
     */
    public Integer checkTrainOperatorLine(Admin admin) {
        return admin.checkLine(this);
    }

    /**
     * Fixes the specified Train's maintenance issues.
     * The method sets the status of the Train to OUT_OF_SERVICE and generates a random cost for the maintenance.
     *
     * @param train The Train that needs to be fixed.
     */
    public void fixTrain(Train train) {
        train.setStatus(Train.Status.OUT_OF_SERVICE);
        Random rand = new Random();
        int cost = rand.nextInt(5000 + 1) + 5000;  // Generates a random number between 5000 and 10000
        // TODO There should be a statistics entry here
    }

}