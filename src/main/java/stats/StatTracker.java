package stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for tracking statistics.
 */
public class StatTracker {

    /**
     * Stores the list of stat entries.
     */
    private final List<TimestampedStatEntry> statEntries = new ArrayList<>();

    /**
     * Return the list of stat entries.
     */
    public List<TimestampedStatEntry> getStatEntries() {
        return Collections.unmodifiableList(statEntries);
    }

    /**
     * Record a stat entry.
     */
    public void record(StatEntry entry) {
        statEntries.add(new TimestampedStatEntry(entry));
    }

    /**
     * Record a stat entry with a timestamp.
     */
    public void recordTimestamped(StatEntry entry, long timestamp) {
        statEntries.add(new TimestampedStatEntry(entry, timestamp));
    }

    /**
     * Return the stats in the time period.
     */
    public List<StatEntry> getStatsInTimePeriod(long afterTime, long beforeTime) {
        List<StatEntry> entries = new ArrayList<>();

        for (TimestampedStatEntry statEntry : statEntries) {
            long timestamp = statEntry.getTimestamp();
            if (timestamp < afterTime || timestamp > beforeTime) continue;
            entries.add(statEntry.getComponent());
        }

        return entries;
    }

}
