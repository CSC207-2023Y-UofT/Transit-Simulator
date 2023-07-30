package stats.aggregator.expense;

import stats.aggregate.DoubleAggregate;
import stats.aggregator.DoubleAggregator;
import stats.aggregator.StatAggregator;
import stats.entry.impl.ExpenseStat;

import java.util.List;

/**
 * Class for aggregating expenses.
 */
public class ExpenseAggregator extends DoubleAggregator<ExpenseStat, ExpenseAggregate> {
    public ExpenseAggregator() {
        super(ExpenseStat.class,
                ExpenseAggregate.class,
                ExpenseStat::getExpense,
                ExpenseAggregate::new);
    }
}
