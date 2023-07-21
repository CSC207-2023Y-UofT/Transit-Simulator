package stats;

public class TrainUsageEvent implements StatEntry{

    private final int line;

    public TrainUsageEvent(int line) { this.line = line; }

    public int getLine() { return line; }

}
