package stats;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class SubwayEmergencyAggregator implements StatAggregator {

    private int total = 0;

    private Map<Integer, Integer> emergencyCounts = new HashMap<>();

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

    public int getTotal() { return total; }

}

