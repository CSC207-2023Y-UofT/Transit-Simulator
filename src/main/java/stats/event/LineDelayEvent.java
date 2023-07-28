package stats.event;

import stats.StatEntry;

/**
 * Event for when a line is delayed.
 */
public class LineDelayEvent implements StatEntry {

    /**
     * The line that is delayed.
     */
    private final int line;

    /**
     * Create a new LineDelayEvent.
     */
    public LineDelayEvent(int line) {
        this.line = line;
    }

    /**
     * Return the line that is delayed.
     */
    public int getLine() { return line; }

}
