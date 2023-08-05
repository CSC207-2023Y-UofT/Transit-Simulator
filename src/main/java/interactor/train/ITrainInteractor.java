package interactor.train;

import java.util.List;

public interface ITrainInteractor {
    TrainState getTrainState(String trainName);
    List<TrainState> getTrains();
}
