package interactor.station;

import java.util.List;

public class StationState {
    private final String name;
    private final List<Integer> lineProfiles;
    private final int x;
    private final int y;

    public StationState(String name, List<Integer> lineProfiles, int x, int y) {
        this.name = name;
        this.lineProfiles = lineProfiles;
        this.x = x;
        this.y = y;
    }

    public List<Integer> getLineProfiles() {
        return lineProfiles;
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
