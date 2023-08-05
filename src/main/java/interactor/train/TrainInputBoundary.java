package interactor.train;

import java.util.List;

public interface TrainInputBoundary {
    TrainState getTrainState(String trainName);
    List<TrainState> getTrains();
}
