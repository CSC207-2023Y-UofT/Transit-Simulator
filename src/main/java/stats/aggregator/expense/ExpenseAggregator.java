package stats.aggregator.expense;

import stats.aggregator.SingletonAggregator;
import stats.entry.impl.ExpenseStat;

/**
 * Class for aggregating expenses.
 */
public class ExpenseAggregator extends SingletonAggregator<ExpenseStat, ExpenseAggregate> {
    public ExpenseAggregator() {
        super(ExpenseStat.class,
                ExpenseAggregate.class,
                ExpenseStat::getExpense,
                ExpenseAggregate::new);
    }
}
