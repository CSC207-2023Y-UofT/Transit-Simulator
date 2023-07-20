package stats;

import java.util.List;

public interface StatAggregator {
    void aggregate(List<StatEntry> entries);
}





