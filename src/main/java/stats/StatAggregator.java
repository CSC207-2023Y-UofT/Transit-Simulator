package stats;

import java.util.List;

/**
 * Interface for classes that aggregate statistics.
 */
public interface StatAggregator {

    /**
     * Aggregate the statistics.
     */
    void aggregate(List<StatEntry> entries);

}