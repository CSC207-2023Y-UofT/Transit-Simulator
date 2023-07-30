package stats.aggregator.revenue;

import stats.aggregator.SingletonAggregator;
import stats.entry.impl.RevenueStat;

/**
 * Class for aggregating expenses.
 */
public class RevenueAggregator extends SingletonAggregator<RevenueStat, RevenueAggregate> {
    public RevenueAggregator() {
        super(RevenueStat.class,
                RevenueAggregate.class,
                s -> new RevenueAggregate(s.getRevenue()),
                (a1, a2) -> new RevenueAggregate(a1.getTotal() + a2.getTotal()));
    }
}
