package presenter;

import interactor.station.StationInteractor;
import interactor.station.StationState;
import model.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TransitMapPassengerPresenter extends TransitMapPresenter {

    private StationState selectedStation = null;
    private Map<Integer, Map<Direction, Long>> arrivalDelays = null;

    public TransitMapPassengerPresenter(StationInteractor stationInteractor) {
        super(stationInteractor);
    }

    public StationState getSelectedStation() {
        return selectedStation;
    }

    public Map<Integer, Map<Direction, Long>> getArrivalDelays() {
        return arrivalDelays;
    }

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
