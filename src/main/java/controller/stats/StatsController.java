package controller.stats;

import interactor.stat.IStatInteractor;
import stats.aggregator.expense.ExpenseAggregate;
import stats.aggregator.revenue.RevenueAggregate;

import java.util.List;

public class StatsController {
    private final IStatInteractor interactor;

    public StatsController(IStatInteractor interactor) {
        this.interactor = interactor;
    }

    public List<RevenueAggregate> getRevenue(long horizonMinutes) {
        return interactor.getRevenue(horizonMinutes);
    }

    public List<ExpenseAggregate> getExpenses(long horizonMinutes) {
        return interactor.getExpenses(horizonMinutes);
    }

}
