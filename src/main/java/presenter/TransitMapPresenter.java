package presenter;

import interactor.station.StationInteractor;
import interactor.station.StationState;
import model.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransitMapPresenter {

    public static final double MAP_SIZE_X = 4000.0;
    public static final double MAP_SIZE_Y = 4000.0;

    private static final int STATION_ICON_SIZE = 8;

    protected final StationInteractor stationInteractor;

    protected List<StationState> stations = new ArrayList<>();

    private StationState highlightedStation = null;

    private int width = 1;
    private int height = 1;

    public TransitMapPresenter(StationInteractor stationInteractor) {
        this.stationInteractor = stationInteractor;
    }

    public void present(Graphics2D graphics, int width, int height) {

        this.stations = stationInteractor.getStations();
        this.width = width;
        this.height = height;

        List<Color> lineColours = new ArrayList<>();
        lineColours.add(Color.ORANGE);
        lineColours.add(Color.GREEN.darker());
        lineColours.add(Color.RED.darker());
        lineColours.add(Color.BLUE);
        lineColours.add(Color.CYAN.darker());

        // Background
        graphics.setColor(Color.LIGHT_GRAY.brighter());
        graphics.fillRect(0, 0, width, height);

        double scaleX = width / MAP_SIZE_X;
        double scaleY = height / MAP_SIZE_Y;

        // For each station, get the next station and draw a line between them
        for (StationState station : stations) {
            for (int line : station.getLines()) {
                Optional<StationState> optNextStation = stationInteractor.getNextStation(line, station.getName(), Direction.FORWARD);
                if (optNextStation.isEmpty()) continue;
                StationState nextStation = optNextStation.get();
                int x = (int) (station.getX() * scaleX);
                int y = (int) (station.getY() * scaleY);

                int nextX = (int) (nextStation.getX() * scaleX);
                int nextY = (int) (nextStation.getY() * scaleY);

                graphics.setColor(lineColours.get(line));
                graphics.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                graphics.drawLine(x, y, nextX, nextY);

                graphics.setColor(Color.BLACK);
            }
        }

        // Draw each station as a dark green circle with a black border
        for (StationState station : stations) {
            int x = (int) (station.getX() * scaleX);
            int y = (int) (station.getY() * scaleY);

            graphics.setColor(Color.DARK_GRAY);
            if (highlightedStation != null) {
                String highlightedName = highlightedStation.getName();
                if (highlightedName.equals(station.getName())) {
                    graphics.setColor(Color.GREEN);
                }
            }

            graphics.fillOval(x - STATION_ICON_SIZE,
                    y - STATION_ICON_SIZE,
                    STATION_ICON_SIZE * 2,
                    STATION_ICON_SIZE * 2);

            graphics.setColor(Color.BLACK);
            graphics.drawOval(x - STATION_ICON_SIZE,
                    y - STATION_ICON_SIZE,
                    STATION_ICON_SIZE * 2,
                    STATION_ICON_SIZE * 2);

            // Draw their name to the upper-right
            graphics.setFont(new Font("Arial", Font.BOLD, 12));
            graphics.drawString(station.getName(), x + STATION_ICON_SIZE, y - STATION_ICON_SIZE);
        }
    }

    public Optional<StationState> getStationAt(int x, int y) {
        double scaleX = width / MAP_SIZE_X;
        double scaleY = height / MAP_SIZE_Y;

        for (StationState station : stations) {
            int stationX = (int) (station.getX() * scaleX);
            int stationY = (int) (station.getY() * scaleY);

            if (Math.abs(stationX - x) < STATION_ICON_SIZE && Math.abs(stationY - y) < STATION_ICON_SIZE) {
                return Optional.of(station);
            }
        }

        return Optional.empty();
    }

    public void onMouseMove(int x, int y) {

        Optional<StationState> optStation = getStationAt(x, y);
        if (optStation.isEmpty()) {
            highlightedStation = null;
            return;
        }

        highlightedStation = optStation.get();
    }

    public void onClick(int x, int y) {
        Optional<StationState> optStation = getStationAt(x, y);
        if (optStation.isEmpty()) return;
        StationState station = optStation.get();

        onClickStation(station);
    }

    protected void onClickStation(StationState station) {}
}
