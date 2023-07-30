package stats.aggregator;

public class ExpenseAggregate {
    private final double expensesTotal;

    public ExpenseAggregate(double expensesTotal) {
        this.expensesTotal = expensesTotal;
    }

    public double getExpensesTotal() {
        return expensesTotal;
    }
}
