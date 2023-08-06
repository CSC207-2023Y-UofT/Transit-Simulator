package controller.stats;

import interactor.stat.IStatInteractor;
import stats.aggregator.expense.ExpenseAggregate;
import stats.aggregator.revenue.RevenueAggregate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class StatsController {
    private final IStatInteractor interactor;

    public StatsController(IStatInteractor interactor) {
        this.interactor = interactor;
    }

    public CompletableFuture<List<RevenueAggregate>> getRevenue(long horizonMinutes) {
        return CompletableFuture.supplyAsync(() -> interactor.getRevenue(horizonMinutes));
    }

    public CompletableFuture<List<ExpenseAggregate>> getExpenses(long horizonMinutes) {
        return CompletableFuture.supplyAsync(() -> interactor.getExpenses(horizonMinutes));
    }

}
