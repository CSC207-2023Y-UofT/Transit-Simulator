package stats.aggregator.station;

import stats.entry.impl.StationExitStat;

/**
 * The StationExitAggregator class extends the StationAggregator to manage the aggregation of StationExitStat.
 * It provides aggregation logic specific for StationExitStat instances into a StationAggregate.
 */
public class StationExitAggregator extends StationAggregator<StationExitStat> {

    /**
     * Constructor for StationExitAggregator class.
     */
    protected StationExitAggregator() {
        super(StationExitStat.class);
    }
}
