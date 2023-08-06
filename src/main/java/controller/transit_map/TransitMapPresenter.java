package controller.transit_map;

import interactor.station.IStationInteractor;
import interactor.station.StationState;
import interactor.train.ITrainInteractor;
import interactor.train.TrainNodeDistance;
import interactor.train.TrainState;
import model.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransitMapPresenter {
    /**
     * The size of the map width in metres.
     */
    public static final double MAP_SIZE_X = 6150.0;
    /**
     * The size of the map height in metres.
     */
    public static final double MAP_SIZE_Y = 4500.0;
    /**
     * The size of the station icons in pickles (pixels).
     */
    private static final int STATION_ICON_SIZE = 7;
    /**
     * The station interactor.
     */
    protected final IStationInteractor stationInteractor;
    /**
     * The train interactor.
     */
    private final ITrainInteractor trainInteractor;
    /**
     * The list of stations to draw.
     */
    protected List<StationState> stations = new ArrayList<>();
    /**
     * The list of trains to draw.
     */
    protected List<TrainState> trains = new ArrayList<>();
    /**
     * The station that is currently highlighted.
     */
    private StationState highlightedStation = null;
    /**
     * The width of the image.
     */
    private int width = 1;
    /**
     * The height of the image.
     */
    private int height = 1;

    /**
     * Constructs a new TransitMapPresenter with the given station and train interactors.
     *
     * @param stationInteractor The station interactor.
     * @param trainInteractor   The train interactor.
     */
    public TransitMapPresenter(IStationInteractor stationInteractor,
                               ITrainInteractor trainInteractor) {
        this.stationInteractor = stationInteractor;
        this.trainInteractor = trainInteractor;
    }

    /**
     * Presents (draws) the screen.
     */
    public void present(Graphics2D graphics, int width, int height) {

        this.stations = stationInteractor.getStations();
        this.trains = trainInteractor.getTrains();

        this.width = width;
        this.height = height;

        List<Color> lineColours = new ArrayList<>();
        lineColours.add(new Color(255, 206, 47));
        lineColours.add(new Color(113, 194, 113));
        lineColours.add(new Color(41, 163, 217));
        lineColours.add(new Color(192, 38, 192));
        lineColours.add(Color.PINK);

        // Background
        graphics.setColor(new Color(200, 180, 180));
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

                graphics.setColor(lineColours.get(line - 1));
                Stroke stroke = graphics.getStroke();
                graphics.setStroke(new BasicStroke(11, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
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

                graphics.setColor(new Color(255, 255, 255));
                graphics.fillOval((int) stationX - 3, (int) stationY - 3, 8, 8);

                // Draw a little border
                graphics.setColor(Color.BLACK);
                graphics.drawOval((int) stationX - 3, (int) stationY - 3, 8, 8);

                // Draw the train's name
                graphics.setFont(new Font("Serif", Font.PLAIN, 12));
                graphics.drawString(train.getName(), (int) stationX + 5, (int) stationY - 5);

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
            graphics.setColor(new Color(255, 255, 255));
            graphics.fillOval((int) trainX - 3, (int) trainY - 3, 8, 8);

            // Draw a little border
            graphics.setColor(Color.BLACK);
            graphics.drawOval((int) trainX - 3, (int) trainY - 3, 8, 8);

            // Train's name
            graphics.setFont(new Font("Serif", Font.PLAIN, 10));
            graphics.drawString(train.getName(), (int) trainX + 6, (int) trainY - 6);
        }
    }

    /**
     * Returns the station at the given coordinates, if any.
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return The station at the given coordinates, if any.
     */
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

    /**
     * Called when the mouse moves.
     *
     * @param x The x-coordinate of the mouse
     * @param y The y-coordinate of the mouse
     */
    public void onMouseMove(int x, int y) {
        Optional<StationState> optStation = getStationAt(x, y);
        if (optStation.isEmpty()) {
            highlightedStation = null;
            return;
        }

        highlightedStation = optStation.get();
    }

    /**
     * Called when the mouse is clicked.
     *
     * @param x The x-coordinate of the mouse
     * @param y The y-coordinate of the mouse
     */
    public void onClick(int x, int y) {
        Optional<StationState> optStation = getStationAt(x, y);
        if (optStation.isEmpty()) return;
        StationState station = optStation.get();

        onClickStation(station);
    }

    /**
     * Called when a station is clicked.
     *
     * @param station The station that was clicked.
     */
    protected void onClickStation(StationState station) {
    }
}
