package stats;

/**
 * Event for a subway emergency.
 */
public class SubwayEmergencyEvent implements StatEntry {

    /**
     * The line of the subway emergency.
     */
    private final int line;

    /**
     * Create a new SubwayEmergencyEvent.
     */
    public SubwayEmergencyEvent(int line) {
        this.line = line;
    }

    /**
     * Return the line of the subway emergency.
     */
    public int getLine() { return line; }

}
