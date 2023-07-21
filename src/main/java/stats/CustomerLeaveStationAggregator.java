package stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerLeaveStationAggregator implements StatAggregator {

    private final Map<String, Integer> entries = new HashMap<>();

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

    public int getLeaves(String station) {
        return entries.getOrDefault(station, 0);
    }
}
