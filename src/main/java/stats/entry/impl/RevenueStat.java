package stats.entry.impl;

import stats.entry.StatEntry;

/**
 * An interface that represents a revenue statistic entry. This entry extends the {@link StatEntry} interface.
 * Implementing classes should provide the logic for how to get the revenue value.
 */
public interface RevenueStat extends StatEntry {

    /**
     * Returns the revenue value for this statistic entry.
     *
     * @return The revenue value as a double.
     */
    double getRevenue();
}
