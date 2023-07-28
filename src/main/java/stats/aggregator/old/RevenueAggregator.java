package stats.aggregator.old;

import stats.RevenueStat;
import stats.StatAggregator;
import stats.StatEntry;

import java.util.List;
import java.util.Map;

/**
 * Aggregator for the total revenue.
 */
public class RevenueAggregator implements StatAggregator {

    /**
     * The total revenue.
     */
    private double revenue = 0.0;

    /**
     * Aggregate the total revenue.
     */
    @Override
    public void aggregate(List<StatEntry> entries) {

        double total = 0.0;
        for (StatEntry stat : entries) {
            if (!(stat instanceof RevenueStat)) continue;
            RevenueStat saleStat = (RevenueStat) stat;
            total += saleStat.getRevenue();
        }

        this.revenue = total;
    }

    /**
     * Return the total revenue.
     */
    public double getRevenue() {
        return revenue;
    }


}
