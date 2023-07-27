package stats;

import java.util.List;

public class ProfitAggregator implements StatAggregator {

    private double profit = 0.0;

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

    public double getProfit() {
        return profit;
    }

}
