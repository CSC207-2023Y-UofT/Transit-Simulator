package stats.entry.impl;

import stats.entry.StatEntry;

public abstract class StationStat implements StatEntry {
    private final String stationName;

    protected StationStat(String stationName) {
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }
}
