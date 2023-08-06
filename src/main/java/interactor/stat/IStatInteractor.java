package interactor.stat;

import stats.aggregator.expense.ExpenseAggregate;
import stats.aggregator.revenue.RevenueAggregate;

import java.util.List;

public interface IStatInteractor {
    // Technically, Aggregates can be thought of as in the Use-Case/Interactor layer
    List<RevenueAggregate> getRevenue(long horizonMinutes);
    List<ExpenseAggregate> getExpenses(long horizonMinutes);

    // TODO more stats
}