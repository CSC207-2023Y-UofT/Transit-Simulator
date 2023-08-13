package app_business.boundary;

import stats.aggregate.ExpenseAggregate;
import stats.aggregate.RevenueAggregate;

import java.util.List;

/**
 * Interactor for the statistics use cases
 */
public interface IStatInteractor {
    // Technically, Aggregates can be thought of as in the Use-Case/Interactor layer

    /**
     * Get the revenue aggregates for the last horizon minutes
     *
     * @param horizon The number of minutes to look back
     * @return The revenue aggregates
     */
    List<RevenueAggregate> getRevenue(long horizon);

    /**
     * Get the expense aggregates for the last horizon minutes
     *
     * @param horizon The number of minutes to look back
     * @return The expense aggregates
     */
    List<ExpenseAggregate> getExpenses(long horizon);

}
