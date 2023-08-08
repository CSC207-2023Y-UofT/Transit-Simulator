package interactor.stat;

import stats.aggregator.expense.ExpenseAggregate;
import stats.aggregator.expense.ExpenseAggregator;
import stats.aggregator.revenue.RevenueAggregate;
import stats.aggregator.revenue.RevenueAggregator;
import stats.persistence.StatDataController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StatInteractor implements IStatInteractor {

    private final StatDataController stats;

    public StatInteractor(StatDataController stats) {
        this.stats = stats;
    }

    @Override
    public List<RevenueAggregate> getRevenue(long horizonMinutes) {
        RevenueAggregator revenueAggregator = new RevenueAggregator();
        long currIndex = System.currentTimeMillis() / IStatInteractor.TIME_INTERVAL;

        List<RevenueAggregate> revenueAggregates = new ArrayList<>();

        Map<Long, RevenueAggregate> aggregateMap = stats.getOrAggregate(revenueAggregator,
                currIndex - horizonMinutes,
                currIndex);

        stats.aggregateCurrent(revenueAggregator)
                .ifPresent(a -> aggregateMap.put(currIndex, a));

        for (long i = currIndex - horizonMinutes; i <= currIndex; i++) {
            Optional<RevenueAggregate> revenueAggregate = Optional.ofNullable(aggregateMap.get(i));

            revenueAggregate.ifPresentOrElse(revenueAggregates::add,
                    () -> revenueAggregates.add(new RevenueAggregate(0)));
        }

        return revenueAggregates;
    }

    @Override
    public List<ExpenseAggregate> getExpenses(long horizonMinutes) {
        ExpenseAggregator expenseAggregator = new ExpenseAggregator();
        long currIndex = System.currentTimeMillis() / IStatInteractor.TIME_INTERVAL;

        List<ExpenseAggregate> expenseAggregates = new ArrayList<>();

        Map<Long, ExpenseAggregate> aggregateMap = stats.getOrAggregate(expenseAggregator,
                currIndex - horizonMinutes,
                currIndex);

        stats.aggregateCurrent(expenseAggregator)
                .ifPresent(a -> aggregateMap.put(currIndex, a));

        for (long i = currIndex - horizonMinutes; i <= currIndex; i++) {
            Optional<ExpenseAggregate> expenseAggregate = Optional.ofNullable(aggregateMap.get(i));

            expenseAggregate.ifPresentOrElse(expenseAggregates::add,
                    () -> expenseAggregates.add(new ExpenseAggregate(0)));
        }

        return expenseAggregates;
    }
}
