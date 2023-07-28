package stats;

/**
 * Event for a train usage.
 */
public class TrainUsageEvent implements StatEntry{

    /**
     * The line of the train usage.
     */
    private final int line;

    /**
     * Create a new TrainUsageEvent.
     */
    public TrainUsageEvent(int line) { this.line = line; }

    /**
     * Return the line of the train usage.
     */
    public int getLine() { return line; }

}
