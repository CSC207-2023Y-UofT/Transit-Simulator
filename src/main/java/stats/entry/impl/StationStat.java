package stats.entry.impl;

import stats.entry.StatEntry;

/**
 * An abstract class that represents a station-related statistic entry. This class implements the {@link StatEntry} interface.
 * It provides a common structure for all types of station statistics with a station name.
 */
public abstract class StationStat implements StatEntry {

    /**
     * The name of the station for which the statistics are applicable.
     */
    private final String stationName;

    /**
     * Constructs a StationStat instance with a specified station name.
     *
     * @param stationName The name of the station for which the statistics are applicable.
     */
    protected StationStat(String stationName) {
        this.stationName = stationName;
    }

    /**
     * Returns the name of the station for this statistic entry.
     *
     * @return The station name as a String.
     */
    public String getStationName() {
        return stationName;
    }

}
