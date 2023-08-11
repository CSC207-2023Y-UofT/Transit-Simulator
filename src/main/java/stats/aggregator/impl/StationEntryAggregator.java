package stats.aggregator.impl;

import stats.entry.impl.station.StationEntryStat;

/**
 * The StationEntryAggregator class extends the StationAggregator to manage the aggregation of station entry data.
 * This class specifically deals with StationEntryStat entries.
 */
public class StationEntryAggregator extends StationAggregator<StationEntryStat> {

    /**
     * Constructor for the StationEntryAggregator class.
     */
    protected StationEntryAggregator() {
        super(StationEntryStat.class);
    }

}