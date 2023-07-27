package stats;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class TrainUsageAggregator implements StatAggregator {

    private static int total = 0;

    private Map<Integer, Integer> usageCounts = new HashMap<>();

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

    public static int getTotal() {
        return total;
    }


}


