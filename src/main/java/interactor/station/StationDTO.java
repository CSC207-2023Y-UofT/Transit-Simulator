package interactor.station;

import java.util.List;

/**
 * The StationState dataclass represents the state of a station.
 */
public class StationDTO {
    /**
     * The name of the station.
     */
    private final String name;
    /**
     * The lines that the station is on.
     */
    private final List<Integer> lines;
    /**
     * The x coordinate of the station.
     */
    private final int x;
    /**
     * The y coordinate of the station.
     */
    private final int y;

    /**
     * Constructs a new StationState with the given name, lines, x and y coordinates.
     *
     * @param name  The name of the station.
     * @param lines The lines that the station is on.
     * @param x     The x coordinate of the station.
     * @param y     The y coordinate of the station.
     */
    public StationDTO(String name, List<Integer> lines, int x, int y) {
        this.name = name;
        this.lines = lines;
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the lines that the station is on.
     *
     * @return The lines that the station is on.
     */
    public List<Integer> getLines() {
        return lines;
    }

    /**
     * Gets the name of the station.
     *
     * @return The name of the station.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the x coordinate of the station.
     *
     * @return The x coordinate of the station.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y coordinate of the station.
     *
     * @return The y coordinate of the station.
     */
    public int getY() {
        return y;
    }
}
