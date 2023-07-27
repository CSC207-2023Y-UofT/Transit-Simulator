package stats;

import java.util.List;
import java.util.Map;

public class RevenueAggregator implements StatAggregator {

    private double revenue = 0.0;

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

    public double getRevenue() {
        return revenue;
    }
}
