package stats.aggregator.expense;

import stats.aggregator.SingletonAggregator;
import stats.entry.impl.ExpenseStat;

/**
 * Class for aggregating expenses.
 * ExpenseAggregator extends SingletonAggregator specifically to manage ExpenseStat and ExpenseAggregate objects.
 * This class provides methods to aggregate individual ExpenseStat objects into an ExpenseAggregate.
 */
public class ExpenseAggregator extends SingletonAggregator<ExpenseStat, ExpenseAggregate> {

    /**
     * Constructor for ExpenseAggregator.
     * This constructs an ExpenseAggregator with defined aggregation and combination logic for ExpenseStat and ExpenseAggregate.
     * <p>
     * The aggregation logic is defined by creating a new ExpenseAggregate from an ExpenseStat.
     * The combination logic is defined by adding the values of two ExpenseAggregates.
     */
    public ExpenseAggregator() {
        super(ExpenseStat.class,
                ExpenseAggregate.class,
                s -> new ExpenseAggregate(s.getExpense()),
                (a1, a2) -> new ExpenseAggregate(a1.getValue() + a2.getValue()));
    }

}
