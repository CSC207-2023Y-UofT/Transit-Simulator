package presenter;

import interactor.station.IStationInteractor;
import interactor.station.StationInteractor;
import interactor.station.StationState;
import interactor.train.ITrainInteractor;
import interactor.train.TrainInteractor;
import model.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The TransitMapPassengerPresenter class is a presenter class that handles the logic for the transit map view for
 * passengers.
 */
public class TransitMapPassengerPresenter extends TransitMapPresenter {

    /**
     * The currently selected station.
     */
    private StationState selectedStation = null;
    /**
     * The arrival delays for the currently selected station.
     */
    private Map<Integer, Map<Direction, Long>> arrivalDelays = null;

    /**
     * Constructs a new TransitMapPassengerPresenter with the given station and train interactors.
     *
     * @param stationInteractor The StationInteractor to use.
     * @param trainInteractor   The TrainInteractor to use.
     */
    public TransitMapPassengerPresenter(IStationInteractor stationInteractor,
                                        ITrainInteractor trainInteractor) {
        super(stationInteractor, trainInteractor);
    }

    /**
     * Gets the currently selected station.
     *
     * @return The currently selected station.
     */
    public StationState getSelectedStation() {
        return selectedStation;
    }

    /**
     * Gets the arrival delays for the currently selected station.
     *
     * @return The arrival delays for the currently selected station.
     */
    public Map<Integer, Map<Direction, Long>> getArrivalDelays() {
        return arrivalDelays;
    }

    /**
     * Retrieves information about the currently selected station.
     *
     * @param station The currently selected station's StationState.
     */
    @Override
    protected void onClickStation(StationState station) {
        super.onClickStation(station);

        Map<Integer, Map<Direction, Long>> arrivalDelays = new HashMap<>();

        for (int line : station.getLines()) {
            Map<Direction, Long> delays = new HashMap<>();
            Optional<Long> arrivalDelayForward = stationInteractor.getTimeTillNextArrival(
                    station.getName(),
                    line,
                    Direction.FORWARD
            );

            Optional<Long> arrivalDelayBackward = stationInteractor.getTimeTillNextArrival(
                    station.getName(),
                    line,
                    Direction.BACKWARD
            );

            arrivalDelayForward.ifPresent(delay -> delays.put(Direction.FORWARD, delay));
            arrivalDelayBackward.ifPresent(delay -> delays.put(Direction.BACKWARD, delay));

            arrivalDelays.put(line, delays);
        }

        if (arrivalDelays.isEmpty()) return;

        this.arrivalDelays = arrivalDelays;
        this.selectedStation = station;

    }
}
