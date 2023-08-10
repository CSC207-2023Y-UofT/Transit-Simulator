package interface_adapter.stats;

import app_business.stat.IStatInteractor;
import stats.aggregator.expense.ExpenseAggregate;
import stats.aggregator.revenue.RevenueAggregate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Controller for stats.
 * Basically just a delegator that does the stuff asynchronously.
 */
public class StatsController {

    /**
     * The interactor
     */
    private final IStatInteractor interactor;

    /**
     * The constructor
     */
    public StatsController(IStatInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Gets the revenue asynchronously
     * @param horizonMinutes How far back to look in minutes
     * @return A CompletableFuture that will complete with the revenue
     */
    public CompletableFuture<List<RevenueAggregate>> getRevenue(long horizonMinutes) {
        return CompletableFuture.supplyAsync(() -> interactor.getRevenue(horizonMinutes));
    }

    /**
     * Gets the expenses asynchronously
     * @param horizonMinutes How far back to look in minutes
     * @return A CompletableFuture that will complete with the expenses
     */
    public CompletableFuture<List<ExpenseAggregate>> getExpenses(long horizonMinutes) {
        return CompletableFuture.supplyAsync(() -> interactor.getExpenses(horizonMinutes));
    }

}
