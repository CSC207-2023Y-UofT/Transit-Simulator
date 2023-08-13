package stats.timing;

/**
 * An interface that represents a time index provider.
 */
public interface TimeIndexingStrategy {
    /**
     * Get the current time index.
     *
     * @return The current time index.
     */
    long getTimeIndex();

    /**
     * Get the time index for a given epoch time.
     *
     * @param epochTime The epoch time to get the index for.
     * @return The time index for the given epoch time.
     */
    long getTimeIndex(long epochTime);
}
