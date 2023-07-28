package stats.event;

import stats.ExpenseStat;

/**
 * Event for when a maintenance event occurs.
 */
public class MaintenanceEvent implements ExpenseStat {

    /**
     * The cost of the maintenance event.
     */
    private final double cost;

    /**
     * Create a new MaintenanceEvent.
     */
    public MaintenanceEvent(double cost) {
        this.cost = cost;
    }

    /**
     * Return the cost of the maintenance event.
     */
    @Override
    public double getExpense() {
        return cost;
    }

}
