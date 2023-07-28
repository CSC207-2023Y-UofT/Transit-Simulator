package stats.aggregator;

import stats.ExpenseStat;
import stats.StatAggregator;
import stats.StatEntry;

import java.util.List;


/**
 * Aggregator for the total expenses.
 */
public class ExpenseAggregator implements StatAggregator {

    /**
     * The total expenses.
     */
    private double expenses = 0;

    /**
     * Aggregate the total expenses.
     */
    @Override
    public void aggregate(List<StatEntry> stats) {

        double total = 0.0;

        for (StatEntry stat : stats) {
            if (!(stat instanceof ExpenseStat)) continue;
            ExpenseStat expenseStat = (ExpenseStat) stat;
            total = expenseStat.getExpense();
        }

        expenses = total;

    }

    /**
     * Return the total expenses.
     */
    public double getExpense() {
        return expenses;
    }
}
