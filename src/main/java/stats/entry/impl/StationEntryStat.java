package stats.entry.impl;

import stats.entry.StatEntry;

/**
 * This class represents a specific type of {@link StationStat} related to station entry statistics.
 * It extends the StationStat class and inherits its properties and methods.
 * This class can be used to model statistics specifically related to entries at a particular station.
 */
public class StationEntryStat extends StationStat {

    /**
     * Constructs a StationEntryStat instance with a specified station name.
     *
     * @param stationName The name of the station for which this entry statistic is applicable.
     */
    protected StationEntryStat(String stationName) {
        super(stationName);
    }
}
