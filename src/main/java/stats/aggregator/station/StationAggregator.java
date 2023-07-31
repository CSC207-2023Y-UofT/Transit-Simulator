package stats.aggregator.station;

import stats.aggregator.StatAggregator;
import stats.entry.impl.StationStat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class StationAggregator<E extends StationStat> extends StatAggregator<E, StationAggregate> {

    protected StationAggregator(Class<E> entryClass) {
        super(entryClass, StationAggregate.class);
    }

    @Override
    public StationAggregate aggregate(List<E> entries) {
        Map<String, Integer> total = new HashMap<>();
        for (E entry : entries) {
            int curr = total.getOrDefault(entry.getStationName(), 0);
            total.put(entry.getStationName(), curr + 1);
        }
        return new StationAggregate(total);
    }

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
