package stats.entry.impl.station;

/**
 * This class represents a specific type of {@link StationStat} related to station exit statistics.
 * It extends the StationStat class and inherits its properties and methods.
 * This class can be used to model statistics specifically related to exits at a particular station.
 */
public class StationExitStat extends StationStat {

    /**
     * Constructs a StationExitStat instance with a specified station name.
     *
     * @param station The name of the station for which this exit statistic is applicable.
     */
    public StationExitStat(String station) {
        super(station);
    }

}
