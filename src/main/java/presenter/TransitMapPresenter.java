package presenter;

import interactor.station.StationInteractor;
import interactor.station.StationState;
import interactor.train.TrainInteractor;
import interactor.train.TrainNodeDistance;
import interactor.train.TrainState;
import model.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransitMapPresenter {

    public static final double MAP_SIZE_X = 3500.0;
    public static final double MAP_SIZE_Y = 3500.0;

    private static final int STATION_ICON_SIZE = 10;

    protected final StationInteractor stationInteractor;
    private final TrainInteractor trainInteractor;

    protected List<StationState> stations = new ArrayList<>();
    protected List<TrainState> trains = new ArrayList<>();

    private StationState highlightedStation = null;

    private int width = 1;
    private int height = 1;

    public TransitMapPresenter(StationInteractor stationInteractor,
                               TrainInteractor trainInteractor) {
        this.stationInteractor = stationInteractor;
        this.trainInteractor = trainInteractor;
    }

    public void present(Graphics2D graphics, int width, int height) {

        this.stations = stationInteractor.getStations();
        this.trains = trainInteractor.getTrains();

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
                Stroke stroke = graphics.getStroke();
                graphics.setStroke(new BasicStroke(7, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                graphics.drawLine(x, y, nextX, nextY);
                graphics.setStroke(stroke);

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
            graphics.setFont(new Font("Serif", Font.BOLD, 12));
            graphics.drawString(station.getName(), x + STATION_ICON_SIZE, y - STATION_ICON_SIZE);
        }

        // Draw trains
        for (TrainState train : trains) {

            if (train.getCurrentStation().isPresent()) {

                // At a station,
                // draw the train at the station
                StationState station = train.getCurrentStation().get();
                double stationX = station.getX();
                double stationY = station.getY();

                stationX *= scaleX;
                stationY *= scaleY;

                graphics.setColor(Color.RED);
                graphics.fillOval((int) stationX - 3, (int) stationY - 3, 6, 6);

                // Draw a little border
                graphics.setColor(Color.BLACK);
                graphics.drawOval((int) stationX - 3, (int) stationY - 3, 6, 6);

                continue;
            }

            TrainNodeDistance nextDistance = train.getNextNodeDistance().orElse(null);
            if (nextDistance == null) continue;

            TrainNodeDistance prevDistance = train.getPreviousNodeDistance().orElse(null);
            if (prevDistance == null) continue;

            double nextXO = nextDistance.getStation().getX();
            double nextYO = nextDistance.getStation().getY();

            double prevXO = prevDistance.getStation().getX();
            double prevYO = prevDistance.getStation().getY();

            double dx = nextXO - prevXO;
            double dy = nextYO - prevYO;

            // Normalize
            double length = Math.sqrt(dx * dx + dy * dy);
            if (length == 0.0) continue;

            dx /= length;
            dy /= length;

            double trainX = prevXO + dx * prevDistance.getDistance();
            double trainY = prevYO + dy * prevDistance.getDistance();

            trainX *= scaleX;
            trainY *= scaleY;

            // Draw the train as a small red circle
            graphics.setColor(Color.RED);
            graphics.fillOval((int) trainX - 3, (int) trainY - 3, 6, 6);

            // Draw a little border
            graphics.setColor(Color.BLACK);
            graphics.drawOval((int) trainX - 3, (int) trainY - 3, 6, 6);

            // Train's name
            graphics.setFont(new Font("Serif", Font.PLAIN, 10));
            graphics.drawString(train.getName(), (int) trainX + 6, (int) trainY - 6);
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

    protected void onClickStation(StationState station) {
    }
}
