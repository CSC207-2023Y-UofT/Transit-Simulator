package stats;

public class SubwayEmergencyEvent implements StatEntry {

    private final int line;

    public SubwayEmergencyEvent(int line) {
        this.line = line;
    }

    public int getLine() { return line; }
}
