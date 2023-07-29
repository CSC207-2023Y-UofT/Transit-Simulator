package stats.event;

/**
 * Event for when a maintenance event occurs.
 */
public class MaintenanceEvent {

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
    public double getExpense() {
        return cost;
    }

}
