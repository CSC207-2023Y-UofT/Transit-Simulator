package stats.event;

/**
 * Event for a subway emergency.
 */
public class SubwayEmergencyEvent {

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
