package stats.aggregate;

import stats.aggregate.SingletonAggregate;

/**
 * The RevenueAggregate class extends the SingletonAggregate to manage the aggregation of revenue.
 * This class specifically handles Double type values representing total revenue.
 */
public class RevenueAggregate extends SingletonAggregate<Double> {

    /**
     * Constructor for the RevenueAggregate class.
     *
     * @param total The total revenue to be aggregated. This is a double value.
     */
    public RevenueAggregate(double total) {
        super(total);
    }
}
