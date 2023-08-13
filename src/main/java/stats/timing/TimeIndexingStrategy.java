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

}
