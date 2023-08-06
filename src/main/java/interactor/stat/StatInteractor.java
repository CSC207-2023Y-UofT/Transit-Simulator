package interactor.stat;

import stats.aggregator.expense.ExpenseAggregate;
import stats.aggregator.expense.ExpenseAggregator;
import stats.aggregator.revenue.RevenueAggregate;
import stats.aggregator.revenue.RevenueAggregator;
import stats.persistence.StatDataController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StatInteractor implements IStatInteractor {
    private final StatDataController stats;

    public StatInteractor(StatDataController stats) {
        this.stats = stats;
    }

    @Override
    public List<RevenueAggregate> getRevenueLast6Hours() {
        RevenueAggregator revenueAggregator = new RevenueAggregator();
        long currIndex = System.currentTimeMillis() / 60000;

        List<RevenueAggregate> revenueAggregates = new ArrayList<>();

        for (long i = currIndex - 360; i <= currIndex; i++) {
            Optional<RevenueAggregate> expenseAggregate = revenueAggregator.aggregate(stats, i, i);
            expenseAggregate.ifPresentOrElse(revenueAggregates::add, () -> revenueAggregates.add(new RevenueAggregate(0)));
        }

        return revenueAggregates;
    }

    @Override
    public List<ExpenseAggregate> getExpensesLast6Hours() {
        ExpenseAggregator expenseAggregator = new ExpenseAggregator();
        long currIndex = System.currentTimeMillis() / 60000;

        List<ExpenseAggregate> expenseAggregates = new ArrayList<>();

        for (long i = currIndex - 360; i <= currIndex; i++) {
            Optional<ExpenseAggregate> expenseAggregate = expenseAggregator.aggregate(stats, i, i);
            expenseAggregate.ifPresentOrElse(expenseAggregates::add, () -> expenseAggregates.add(new ExpenseAggregate(0)));
        }

        return expenseAggregates;
    }
}
