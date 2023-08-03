package stats.aggregator.station;

import stats.aggregate.SingletonAggregate;

import java.util.Map;

/**
 * The StationAggregate class extends the SingletonAggregate to manage the aggregation of station data.
 * This class specifically deals with Map type values, where each key-value pair represents a station and its corresponding integer value.
 */
public class StationAggregate extends SingletonAggregate<Map<String, Integer>> {

    /**
     * Constructor for the StationAggregate class.
     *
     * @param total The total station data to be aggregated. This is a Map where the key is a String (station name) and the value is an Integer.
     */
    public StationAggregate(Map<String, Integer> total) {
        super(total);
    }

}
