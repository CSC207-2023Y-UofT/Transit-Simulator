package stats.aggregator.station;

import stats.aggregate.SingletonAggregate;

import java.io.Serializable;
import java.util.Map;

public class StationAggregate extends SingletonAggregate<Map<String, Integer>> {
    public StationAggregate(Map<String, Integer> total) {
        super(total);
    }

}
