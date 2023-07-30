package stats.aggregator;

import java.io.Serializable;

public class ExpenseAggregate implements Serializable {
    private final double expensesTotal;

    public ExpenseAggregate(double expensesTotal) {
        this.expensesTotal = expensesTotal;
    }

    public double getExpensesTotal() {
        return expensesTotal;
    }
}
