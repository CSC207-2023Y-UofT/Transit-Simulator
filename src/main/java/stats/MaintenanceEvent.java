package stats;

public class MaintenanceEvent implements ExpenseStat {

    private final double cost;

    public MaintenanceEvent(double cost) {
        this.cost = cost;
    }

    @Override
    public double getExpense() {
        return cost;
    }

}
