package stats;

/**
 * Interface for events that have revenue.
 */
public interface RevenueStat extends StatEntry {

    /**
     * Return the revenue.
     */
    double getRevenue();

}
