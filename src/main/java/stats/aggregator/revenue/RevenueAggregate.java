package stats.aggregator.revenue;

import stats.aggregate.SingletonAggregate;

/**
 * Class for the differentiation of the revenue aggregate.
 */
public class RevenueAggregate extends SingletonAggregate<Double> {
    public RevenueAggregate(double total) {
        super(total);
    }
}
