package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to time certain operations for analysis
 */
public class Timing {

    /** The name of the timing */
    private final String name;

    /** Map of timings for each id */
    private final Map<String, Long> timings = new HashMap<>();

    /** Map of counts for each id */
    private final Map<String, Integer> counts = new HashMap<>();

    /** The current mark, or -1 if not started */
    private long mark = -1;

    /**
     * Creates a new timing with the given name
     *
     * @param name The name of the timing
     */
    public Timing(String name) {
        this.name = name;
    }

    /**
     * Starts the timing
     */
    public void start() {
        mark = System.currentTimeMillis();
    }

    /**
     * Marks the timing with the given id
     *
     * @param id The id to mark
     */
    public void mark(String id) {
        if (mark == -1) {
            throw new IllegalStateException("Timing not started");
        }
        long now = System.currentTimeMillis();
        long diff = now - mark;
        timings.put(id, timings.getOrDefault(id, 0L) + diff);
        counts.put(id, counts.getOrDefault(id, 0) + 1);
        mark = now;
    }

}
