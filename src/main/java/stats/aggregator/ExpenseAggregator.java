package stats.aggregator;

import stats.entry.impl.ExpenseStat;

import java.util.List;

public class ExpenseAggregator extends StatAggregator<ExpenseStat, ExpenseAggregate> {
    @Override
    public Class<ExpenseStat> getEntryClass() {
        return ExpenseStat.class;
    }

    @Override
    public Class<ExpenseAggregate> getAggregateClass() {
        return ExpenseAggregate.class;
    }

    @Override
    public ExpenseAggregate aggregate(List<ExpenseStat> entries) {
        double expenses = 0.0;

        for (ExpenseStat entry : entries) {
            expenses += entry.getExpense();
        }

        return new ExpenseAggregate(expenses);
    }

    @Override
    public ExpenseAggregate aggregateExisting(List<ExpenseAggregate> aggregates) {
        double expenses = 0.0;

        for (ExpenseAggregate aggregate : aggregates) {
            expenses += aggregate.getExpensesTotal();
        }

        return new ExpenseAggregate(expenses);
    }
}
