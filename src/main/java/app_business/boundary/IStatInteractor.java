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
     * The amount of time in milliseconds that one time index represents when it
     * comes to storing/querying stats
     */
    int TIME_INTERVAL = 4000;

    /**
     * Get the revenue aggregates for the last horizonMinutes minutes
     *
     * @param horizonMinutes The number of minutes to look back
     * @return The revenue aggregates
     */
    List<RevenueAggregate> getRevenue(long horizonMinutes);

    /**
     * Get the expense aggregates for the last horizonMinutes minutes
     *
     * @param horizonMinutes The number of minutes to look back
     * @return The expense aggregates
     */
    List<ExpenseAggregate> getExpenses(long horizonMinutes);

}
