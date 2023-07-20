package stats;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class LineDelayAggregator implements StatAggregator{

    private int total = 0;

    private Map<Integer, Integer> delayCounts = new HashMap<>();


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

    public int getTotal() {
        return total;
    }
}
