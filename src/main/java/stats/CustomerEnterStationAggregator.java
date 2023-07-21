package stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerEnterStationAggregator implements StatAggregator{

    private final Map<String, Integer> entries = new HashMap<>();

    @Override
    public void aggregate(List<StatEntry> stats) {

        for (StatEntry stat : stats) {
            if (stat instanceof CustomerEnterStationEvent) {
                CustomerEnterStationEvent event = (CustomerEnterStationEvent) stat;
                entries.putIfAbsent(event.getStation(), 0);
                int curr = entries.get(event.getStation());
                entries.putIfAbsent(event.getStation(), curr + 1);
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
