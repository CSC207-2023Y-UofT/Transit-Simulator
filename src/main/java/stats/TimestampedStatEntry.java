package stats;

/**
 * A StatEntry with a timestamp.
 */
public class TimestampedStatEntry implements StatEntry {

    /**
     * The StatEntry.
     */
    private final StatEntry component;

    /**
     * The timestamp.
     */
    private final long timestamp;

    /**
     * Create a new TimestampedStatEntry.
     */
    public TimestampedStatEntry(StatEntry component, long timestamp) {
        this.component = component;
        this.timestamp = timestamp;
    }

    /**
     * Create a new TimestampedStatEntry with the current time.
     */
    public TimestampedStatEntry(StatEntry component) {
        this(component, System.currentTimeMillis());
    }

    /**
     * Return the StatEntry.
     */
    public StatEntry getComponent() {
        return component;
    }

    /**
     * Return the timestamp.
     */
    public long getTimestamp() {
        return timestamp;
    }
}
