package stats;

public class LineDelayEvent implements StatEntry{

    private final int line;

    public LineDelayEvent(int line) {
        this.line = line;
    }

    public int getLine() { return line; }

}
