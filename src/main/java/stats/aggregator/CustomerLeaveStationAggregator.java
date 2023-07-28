package stats.aggregator;

import stats.event.CustomerLeaveStationEvent;
import stats.StatAggregator;
import stats.StatEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregator for the number of leaves for each station.
 */
public class CustomerLeaveStationAggregator implements StatAggregator {

    /**
     * Map of station name to number of leaves.
     */
    private final Map<String, Integer> entries = new HashMap<>();

    /**
     * Aggregate the number of leaves for each station.
     */
    @Override
    public void aggregate(List<StatEntry> stats) {

        for (StatEntry stat : stats) {
            if (stat instanceof CustomerLeaveStationEvent) {
                CustomerLeaveStationEvent event = (CustomerLeaveStationEvent) stat;
                entries.putIfAbsent(event.getStation(), 0);
                int curr = entries.get(event.getStation());
                entries.putIfAbsent(event.getStation(), curr + 1);
            }
        }

    }

    /**
     * Return the number of leaves that were aggregated
     * for a specific station.
     */
    public int getLeaves(String station) {
        return entries.getOrDefault(station, 0);
    }
}
