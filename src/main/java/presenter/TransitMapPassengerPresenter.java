package presenter;

import interactor.station.StationInteractor;
import interactor.station.StationState;

public class TransitMapPassengerPresenter extends TransitMapPresenter {

    public TransitMapPassengerPresenter(StationInteractor stationInteractor) {
        super(stationInteractor);
    }

    @Override
    protected void onClickStation(StationState station) {
        super.onClickStation(station);
    }
}
