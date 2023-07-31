package stats.aggregator.station;

import stats.entry.impl.StationEntryStat;
import stats.entry.impl.StationExitStat;

public class StationExitAggregator extends StationAggregator<StationExitStat> {
    protected StationExitAggregator() {
        super(StationExitStat.class);
    }
}
