package interactor.stat;

import stats.aggregator.expense.ExpenseAggregate;
import stats.aggregator.expense.ExpenseAggregator;
import stats.aggregator.revenue.RevenueAggregate;
import stats.aggregator.revenue.RevenueAggregator;
import stats.persistence.StatDataController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link IStatInteractor} interface for fetching statistical data.
 * This class provides functionality to fetch aggregated revenue and expense data.
 */
public class StatInteractor implements IStatInteractor {

    /** Controller used to manage statistical data persistence. */
    private final StatDataController stats;

    /**
     * Constructs a new StatInteractor with the specified statistical data controller.
     *
     * @param stats The controller used for statistical data persistence.
     */
    public StatInteractor(StatDataController stats) {
        this.stats = stats;
    }

    /**
     * Retrieves a list of revenue aggregates over a specified time horizon.
     * Aggregation is done based on current system time and the provided horizon.
     *
     * @param horizonMinutes The time horizon in minutes for which to fetch the revenue aggregates.
     * @return A list of {@link RevenueAggregate} objects representing the aggregated revenue data.
     */
    @Override
    public List<RevenueAggregate> getRevenue(long horizonMinutes) {
        RevenueAggregator revenueAggregator = new RevenueAggregator();
        long currIndex = System.currentTimeMillis() / IStatInteractor.TIME_INTERVAL;

        List<RevenueAggregate> revenueAggregates = new ArrayList<>();

        for (long i = currIndex - horizonMinutes; i <= currIndex; i++) {
            Optional<RevenueAggregate> expenseAggregate = revenueAggregator.aggregate(stats, i, i);
            expenseAggregate.ifPresentOrElse(revenueAggregates::add, () -> revenueAggregates.add(new RevenueAggregate(0)));
        }

        return revenueAggregates;
    }

    /**
     * Retrieves a list of expense aggregates over a specified time horizon.
     * Aggregation is done based on current system time and the provided horizon.
     *
     * @param horizonMinutes The time horizon in minutes for which to fetch the expense aggregates.
     * @return A list of {@link ExpenseAggregate} objects representing the aggregated expense data.
     */
    @Override
    public List<ExpenseAggregate> getExpenses(long horizonMinutes) {
        ExpenseAggregator expenseAggregator = new ExpenseAggregator();
        long currIndex = System.currentTimeMillis() / IStatInteractor.TIME_INTERVAL;

        List<ExpenseAggregate> expenseAggregates = new ArrayList<>();

        for (long i = currIndex - horizonMinutes; i <= currIndex; i++) {
            Optional<ExpenseAggregate> expenseAggregate = expenseAggregator.aggregate(stats, i, i);
            expenseAggregate.ifPresentOrElse(expenseAggregates::add, () -> expenseAggregates.add(new ExpenseAggregate(0)));
        }

        return expenseAggregates;
    }
}
