package employee;

import model.train.Train;
import stats.MaintenanceEvent;
import java.util.Random;

public class TrainEngineer extends Employee {

    private boolean isPaid = false;


    public TrainEngineer(int num) {
        super(num);
    }

    /**
     *  returns what this employee obejct is being paid
     *  @return the monthly salary of this employee
     */
    @Override
    public double getMonthlySalary() {
        return 1.2 * super.getMonthlySalary();
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

    /**
     *  fixes Train object's maintenance issues
     *  @param train the train object that needs to be fixed
     */
    public void fixTrain(Train train) {
        train.setStatus(Train.Status.OUT_OF_SERVICE);
        Random rand = new Random();
        int cost = rand.nextInt(5000 + 1) + 5000;  // .nextInt(5000 + 1) will generate from 0-5000, so we add 5000 to get 5000-10000
        MaintenanceEvent event = new MaintenanceEvent(cost); // The price is arbitrarily between 5k and 10k, set by Grace ISO time: 2023-07-27T19:10:07
        // TODO: create a sub-handler for maintenance events on the side of the train or employee
    }

}
