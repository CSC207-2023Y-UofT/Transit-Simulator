package stats.aggregator;

import stats.StatAggregator;
import stats.StatEntry;
import stats.event.SubwayEmergencyEvent;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Aggregator for the total number of subway emergencies.
 */
public class SubwayEmergencyAggregator implements StatAggregator {

    /**
     * The total number of subway emergencies.
     */
    private int total = 0;

    /**
     * The number of subway emergencies per line.
     */
    private Map<Integer, Integer> emergencyCounts = new HashMap<>();

    /**
     * Aggregate the total number of subway emergencies.
     */
    @Override
    public void aggregate(List<StatEntry> stats) {

        for (StatEntry stat : stats) {

            if (!(stat instanceof SubwayEmergencyEvent)) continue;

            SubwayEmergencyEvent event = (SubwayEmergencyEvent) stat;
            int line = event.getLine();

            total++;

            int curr = emergencyCounts.getOrDefault(line, 0);
            emergencyCounts.put(line, curr + 1);

        }
    }

    /**
     * Return the number of subway emergencies per line.
     */
    public int getTotal() { return total; }

}
