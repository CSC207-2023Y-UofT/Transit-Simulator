package interactor.station;

import java.util.List;

public class StationState {
    private final String name;
    private final List<Integer> lines;
    private final int x;
    private final int y;

    public StationState(String name, List<Integer> lines, int x, int y) {
        this.name = name;
        this.lines = lines;
        this.x = x;
        this.y = y;
    }

    public List<Integer> getLines() {
        return lines;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
