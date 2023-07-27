package stats;

import java.util.List;


public class ExpensesAggregator implements StatAggregator {

    private double expenses = 0;

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


    public double getExpenses() {
        return expenses;
    }
}
