package stats.entry.impl.expense;

/**
 * This class represents a specific type of {@link ExpenseStat} related to maintenance expenses.
 * It provides information about the maintenance cost as an expense.
 */
public class MaintenanceStat implements ExpenseStat {

    /**
     * The cost of maintenance represented as a double value.
     */
    private final double cost;

    /**
     * Constructs a MaintenanceStat instance with a specified cost.
     *
     * @param cost The cost of the maintenance.
     */
    public MaintenanceStat(double cost) {
        this.cost = cost;
    }

    /**
     * Constructs a MaintenanceStat instance with a default cost of 0.0.
     */
    public MaintenanceStat() {
        this.cost = 0.0;
    }

    /**
     * Returns the cost of the maintenance.
     *
     * @return The cost as a double value.
     */
    @Override
    public double getExpense() {
        return cost;
    }

}
