package stats.aggregator.impl;

import stats.aggregate.RevenueAggregate;
import stats.entry.impl.revenue.RevenueStat;

/**
 * Class for aggregating revenue.
 * RevenueAggregator extends SingletonAggregator specifically to manage RevenueStat and RevenueAggregate objects.
 * This class provides methods to aggregate individual RevenueStat objects into a RevenueAggregate.
 */
public class RevenueAggregator extends SingletonAggregator<RevenueStat, RevenueAggregate> {

    /**
     * Constructor for RevenueAggregator.
     * This constructs a RevenueAggregator with defined aggregation and combination logic for RevenueStat and RevenueAggregate.
     * <p>
     * The aggregation logic is defined by creating a new RevenueAggregate from a RevenueStat.
     * The combination logic is defined by adding the values of two RevenueAggregates.
     */
    public RevenueAggregator() {
        super(RevenueStat.class,
                RevenueAggregate.class,
                s -> new RevenueAggregate(s.getRevenue()),
                (a1, a2) -> new RevenueAggregate(a1.getValue() + a2.getValue()));
    }

}
