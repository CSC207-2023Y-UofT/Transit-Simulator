package stats.aggregator;

import stats.event.CustomerEnterStationEvent;
import stats.StatAggregator;
import stats.StatEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aggregator for the number of entries for each station.
 */
public class CustomerEnterStationAggregator implements StatAggregator {

    /**
     * Map of station name to number of entries.
     */
    private final Map<String, Integer> entries = new HashMap<>();

    /**
     * Aggregate the number of entries for each station.
     */
    @Override
    public void aggregate(List<StatEntry> stats) {

        for (StatEntry stat : stats) {
            if (stat instanceof CustomerEnterStationEvent) {
                CustomerEnterStationEvent event = (CustomerEnterStationEvent) stat;
                entries.putIfAbsent(event.getStation(), 0);
                int curr = entries.get(event.getStation());
                entries.put(event.getStation(), curr + 1);
            }
        }

    }

    /**
     * Return the number of entries that were aggregated
     * for a specific station.
     */
    public int getEntries(String station) {
        return entries.getOrDefault(station, 0);
    }


}
