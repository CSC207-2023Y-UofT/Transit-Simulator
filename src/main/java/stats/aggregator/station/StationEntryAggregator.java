package stats.aggregator.station;

import stats.entry.impl.StationEntryStat;

public class StationEntryAggregator extends StationAggregator<StationEntryStat> {
    protected StationEntryAggregator() {
        super(StationEntryStat.class);
    }
}
