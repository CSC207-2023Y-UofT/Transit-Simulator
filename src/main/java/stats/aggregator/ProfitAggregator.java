package stats.aggregator;

import stats.StatAggregator;
import stats.StatEntry;

import java.util.List;

/**
 * Aggregator for the total profit.
 */
public class ProfitAggregator implements StatAggregator {

    /**
     * The total profit.
     */
    private double profit = 0.0;

    /**
     * Aggregate the total profit.
     */
    @Override
    public void aggregate(List<StatEntry> entries) {

        // create new revenue and expense aggregators
        RevenueAggregator revenueAggregator = new RevenueAggregator();
        ExpenseAggregator expenseAggregator = new ExpenseAggregator();

        // make revenue and expense aggregate
        revenueAggregator.aggregate(entries);
        expenseAggregator.aggregate(entries);

        // get the aggregated revenue and expense amount
        double revenue = revenueAggregator.getRevenue();
        double expense = expenseAggregator.getExpense();

        // subtract to find the profit
        this.profit = revenue - expense;
    }

    /**
     * Return the total profit.
     */
    public double getProfit() {
        return profit;
    }

}
