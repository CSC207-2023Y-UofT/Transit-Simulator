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

    protected final StationInteractor stationInteractor;

    protected List<StationState> stations = new ArrayList<>();

    private StationState highlightedStation = null;

    public TransitMapPresenter(StationInteractor stationInteractor) {
        this.stationInteractor = stationInteractor;
    }

    public void present(Graphics2D graphics, int width, int height) {
        this.stations = stationInteractor.getStations();

        List<Color> lineColours = new ArrayList<>();
        lineColours.add(Color.YELLOW);
        lineColours.add(Color.GREEN);
        lineColours.add(Color.RED);
        lineColours.add(Color.BLUE);
        lineColours.add(Color.ORANGE);

        // Background
        graphics.setColor(Color.WHITE);
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

            graphics.setColor(Color.GREEN.darker());
            graphics.fillOval(x - 10, y - 10, 20, 20);

            graphics.setColor(Color.BLACK);
            graphics.drawOval(x - 10, y - 10, 20, 20);

            // Draw their name to the upper-right
            graphics.setFont(new Font("Arial", Font.BOLD, 12));
            graphics.drawString(station.getName(), x + 10, y - 25);
        }
    }

    public Optional<StationState> getStationAt(int x, int y) {
        double scaleX = MAP_SIZE_X / x;
        double scaleY = MAP_SIZE_Y / y;

        for (StationState station : stations) {
            int stationX = (int) (station.getX() * scaleX);
            int stationY = (int) (station.getY() * scaleY);

            if (Math.abs(stationX - x) < 10 && Math.abs(stationY - y) < 10) {
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
