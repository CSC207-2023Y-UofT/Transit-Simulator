package stats.aggregator.impl;

import stats.aggregate.StationAggregate;
import stats.aggregator.StatAggregator;
import stats.entry.impl.station.StationStat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for aggregating station data.
 * StationAggregator extends StatAggregator to aggregate StationStat entries into a StationAggregate.
 * Subclasses of StationAggregator should implement their own aggregation logic.
 */
public abstract class StationAggregator<E extends StationStat> extends StatAggregator<E, StationAggregate> {

    /**
     * Constructor for the StationAggregator class.
     *
     * @param entryClass The class of entries that will be aggregated. This should extend StationStat.
     */
    protected StationAggregator(Class<E> entryClass) {
        super(entryClass, StationAggregate.class);
    }

    /**
     * Aggregates a list of entries into a StationAggregate.
     *
     * @param entries List of StationStat entries to be aggregated.
     * @return A StationAggregate object representing the aggregation of the entries.
     */
    @Override
    public StationAggregate aggregate(List<E> entries) {
        Map<String, Integer> total = new HashMap<>();
        for (E entry : entries) {
            int curr = total.getOrDefault(entry.getStationName(), 0);
            total.put(entry.getStationName(), curr + 1);
        }
        return new StationAggregate(total);
    }


    /**
     * Aggregates a list of existing StationAggregate objects into a new StationAggregate.
     *
     * @param aggregates List of existing StationAggregate objects to be aggregated.
     * @return A new StationAggregate object representing the aggregation of the existing aggregates.
     */
    @Override
    public StationAggregate aggregateExisting(List<StationAggregate> aggregates) {
        Map<String, Integer> total = new HashMap<>();
        for (StationAggregate aggregate : aggregates) {
            for (Map.Entry<String, Integer> entry : aggregate.getValue().entrySet()) {
                int curr = total.getOrDefault(entry.getKey(), 0);
                total.put(entry.getKey(), curr + entry.getValue());
            }
        }
        return new StationAggregate(total);
    }

}
