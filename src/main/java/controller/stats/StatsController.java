package controller.stats;

import interactor.stat.IStatInteractor;
import stats.aggregator.expense.ExpenseAggregate;
import stats.aggregator.revenue.RevenueAggregate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Controller class for managing and retrieving statistical data related to revenue and expenses.
 * Uses an implementation of the {@link IStatInteractor} interface to fetch the data.
 */
public class StatsController {

    /** The interactor used for fetching statistical data. */
    private final IStatInteractor interactor;

    /**
     * Constructs a new instance of the StatsController with the provided interactor.
     *
     * @param interactor The interactor responsible for fetching statistical data.
     */
    public StatsController(IStatInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Asynchronously fetches revenue aggregates up to a specified time horizon.
     *
     * @param horizonMinutes The time horizon in minutes.
     * @return A CompletableFuture containing a list of {@link RevenueAggregate}.
     */
    public CompletableFuture<List<RevenueAggregate>> getRevenue(long horizonMinutes) {
        return CompletableFuture.supplyAsync(() -> interactor.getRevenue(horizonMinutes));
    }

    /**
     * Asynchronously fetches expense aggregates up to a specified time horizon.
     *
     * @param horizonMinutes The time horizon in minutes.
     * @return A CompletableFuture containing a list of {@link ExpenseAggregate}.
     */
    public CompletableFuture<List<ExpenseAggregate>> getExpenses(long horizonMinutes) {
        return CompletableFuture.supplyAsync(() -> interactor.getExpenses(horizonMinutes));
    }

}
