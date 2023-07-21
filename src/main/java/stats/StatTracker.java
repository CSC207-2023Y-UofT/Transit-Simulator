package stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatTracker {

    /**
     * Stores the list of stat entries.
     */
    private List<TimestampedStatEntry> statEntries = new ArrayList<>();

    public List<TimestampedStatEntry> getStatEntries() {
        return Collections.unmodifiableList(statEntries);
    }

    public void record(StatEntry entry) {
        statEntries.add(new TimestampedStatEntry(entry));
    }

    public void recordTimestamped(StatEntry entry, long timestamp) {
        statEntries.add(new TimestampedStatEntry(entry, timestamp));
    }

}
