package simulation;

import model.Direction;
import model.control.TransitModel;
import model.train.Train;

public class TrainSimulator {

    private final int tickSpeed;

    public TrainSimulator(int tickSpeed) {
        this.tickSpeed = tickSpeed;
    }

    public void tick(TransitModel model) {
        for (Train train : model.getTrainList()) {

            boolean wasAtStation = train.getPosition().getTrack()
                    .getNode()
                    .isPresent();

            // Move the train a bit
            train.move(Direction.FORWARD, Train.MAX_SPEED / tickSpeed);

            boolean nowAtStation = train.getPosition().getTrack()
                    .getNode()
                    .isPresent();

            if (nowAtStation && !wasAtStation) {
                // The train is arriving at a station
                // Unload passengers
                train.simulateAlighting();
            } else if (wasAtStation && !nowAtStation) {
                // The train is leaving a station
                // Load passengers
                train.handleBoardingPassengers();
            }

        }
    }
}
