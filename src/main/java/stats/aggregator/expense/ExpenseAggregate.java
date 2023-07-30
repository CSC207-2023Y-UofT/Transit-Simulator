package stats.aggregator.expense;

import stats.aggregate.SingletonAggregate;

public class ExpenseAggregate extends SingletonAggregate<Double> {
    public ExpenseAggregate(double total) {
        super(total);
    }
}
