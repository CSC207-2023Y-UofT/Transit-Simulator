package stats.aggregator.revenue;

import stats.aggregator.DoubleAggregator;
import stats.entry.impl.ExpenseStat;
import stats.entry.impl.RevenueStat;

/**
 * Class for aggregating expenses.
 */
public class RevenueAggregator extends DoubleAggregator<RevenueStat, RevenueAggregate> {
    public RevenueAggregator() {
        super(RevenueStat.class,
                RevenueAggregate.class,
                RevenueStat::getRevenue,
                RevenueAggregate::new);
    }
}
