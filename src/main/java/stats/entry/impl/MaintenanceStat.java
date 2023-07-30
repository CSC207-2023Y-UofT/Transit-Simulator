package stats.entry.impl;

/**
 * Example class
 */
public class MaintenanceStat implements ExpenseStat {

    private final double cost;

    public MaintenanceStat(double cost) {
        this.cost = cost;
    }

    public MaintenanceStat() {
        this.cost = 0.0;
    }

    @Override
    public double getExpense() {
        return cost;
    }
}
