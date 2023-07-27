package stats;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Aggregator for the total number of delays for each line.
 */
public class LineDelayAggregator implements StatAggregator{

    /**
     * The total number of delays.
     */
    private int total = 0;

    /**
     * Map of line number to number of delays.
     */
    private Map<Integer, Integer> delayCounts = new HashMap<>();

    /**
     * Aggregate the total number of delays for each line.
     */
    @Override
    public void aggregate(List<StatEntry> stats) {

        for (StatEntry stat : stats) {

            if (!(stat instanceof LineDelayEvent)) continue;

            LineDelayEvent event = (LineDelayEvent) stat;
            int line = event.getLine();

            total++;

            int curr = delayCounts.getOrDefault(line, 0);
            delayCounts.put(line, curr + 1);

        }
    }

    /**
     * Return the number of delays that were aggregated
     * for a specific line.
     */
    public int getTotal() {
        return total;
    }
}
