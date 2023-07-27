package stats;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Aggregator for the total number of train usage.
 */
public class TrainUsageAggregator implements StatAggregator {

    /**
     * The total number of train usage.
     */
    private static int total = 0;

    /**
     * The number of train usage per line.
     */
    private Map<Integer, Integer> usageCounts = new HashMap<>();

    /**
     * Aggregate the total number of train usage.
     */
    @Override
    public void aggregate(List<StatEntry> stats) {

        for (StatEntry stat : stats) {

            if (!(stat instanceof TrainUsageEvent)) continue;

            TrainUsageEvent event = (TrainUsageEvent) stat;
            int line = event.getLine();

            total++;

            int curr = usageCounts.getOrDefault(line, 0);
            usageCounts.put(line, curr + 1);

        }
    }

    /**
     * Return the number of train usage per line.
     */
    public static int getTotal() {
        return total;
    }

}
