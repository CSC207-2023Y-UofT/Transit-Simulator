package stats;

public class TimestampedStatEntry implements StatEntry {

    private final StatEntry component;
    private final long timestamp;

    public TimestampedStatEntry(StatEntry component, long timestamp) {
        this.component = component;
        this.timestamp = timestamp;
    }

    public TimestampedStatEntry(StatEntry component) {
        this(component, System.currentTimeMillis());
    }

    public StatEntry getComponent() {
        return component;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
