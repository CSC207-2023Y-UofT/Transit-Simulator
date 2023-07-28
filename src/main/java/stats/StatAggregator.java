package stats;

import java.util.List;

/**
 * Interface for classes that aggregate statistics.
 */
public interface StatAggregator<T extends StatEntry> {

    /**
     * Aggregate the statistics.
     */
    void aggregate(List<T> entries);

}
