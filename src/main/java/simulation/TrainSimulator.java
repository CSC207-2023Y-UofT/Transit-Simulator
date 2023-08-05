package simulation;

import model.Direction;
import model.control.TransitModel;
import model.train.Passenger;
import model.train.Train;
import ticket.AdultTicket;
import ticket.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TrainSimulator {

    private final int tickSpeed;

    public TrainSimulator(int tickSpeed) {
        this.tickSpeed = tickSpeed;
    }

    public void recreateTrains(TransitModel model) {
        model.clearTrains();


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
                simulateAlighting(train);
            } else if (wasAtStation && !nowAtStation) {
                // The train is leaving a station
                // Load passengers
                simulateBoarding(train);
            }

        }
    }

    /**
     * Handle the alighting passengers on this train.
     *
     * @return the number of passengers that alighted
     */
    private int simulateAlighting(Train train) {
        int counter = 0;

        List<Passenger> passengerList = new ArrayList<>(train.getPassengerList());
        for (Passenger passenger : passengerList) {
            passenger.decrementStationsToTravel();

            if (passenger.shouldAlight()) {
                train.removePassenger(passenger);
                counter++;
            }
        }

        return counter;
    }

    /**
     * Handling simulating the boarding of passengers onto this train.
     * @return the number of passengers that boarded
     */
    private int simulateBoarding(Train train) {
        if (train.getPassengerList().size() >= train.getCapacity()) return 0;
        int numPassengersBoarded = 0;
        while (Math.random() < 0.5) {
            Ticket ticket = new AdultTicket();
            int stationsToTravel = (int) (Math.random() * 5) + 1;
            Passenger passenger = new Passenger(ticket, stationsToTravel);
            train.addPassenger(passenger);
            numPassengersBoarded++;
        }
        return numPassengersBoarded;
    }
}
