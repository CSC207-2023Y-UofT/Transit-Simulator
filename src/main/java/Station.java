import org.jetbrains.annotations.NotNull;  // This was automatically suggested by IntelliJ, idk what it does

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Station extends Node {
    private Map<Integer, Integer> boarding = new TreeMap<>();  // passengers waiting for the next train
    private int disembarking = 0;  // passengers getting off the train

    public String name;
    private boolean endOfDay = false;


    @Override
    public void receiveTrain(Train incomingTrain) {
        /*  Handle receiving trains
         *  On train arrival, controller action:
         *  1) Update the train's location
         *  1.5) hold the train
         *  2) change its direction if this is an endpoint station
         *  3) Handle offloading passengers
         *  4) Handle statistics
         *  5) Make train offline if end of day, or scheduled for maintenance
         *  5.1) Handle onboarding passengers
         */
        super.receiveTrain(incomingTrain);  // 1), 1.5), 2) finished
        this.handleDisembarking(incomingTrain);  // 3) finished
        // 4) TODO: Handle statistics
        // 5) TODO: Make train offline if end of day
        if (this.endOfDay) {
            incomingTrain.setStatus(Train.StatusType.OUT_OF_SERVICE);  // 5) finished
        } else {
            this.handleBoardingPassengers(incomingTrain);  // 5.1) finished
        }
    }

    /**
     *  handle boarding passengers to the incoming train
     *  Always board passengers that ride the least stops before passengers that ride the most stops
     *  @param incomingTrain the train that just arrived at the station
     */
    private void handleBoardingPassengers (Train incomingTrain) {
        this.boarding.replaceAll((k, v) -> incomingTrain.addPassengers(k, this.boarding.get(k)));
    }

    public int getDisembarking () {
        int val = this.disembarking;
        this.disembarking = 0;
        return val;
    }

    /**
     *  handle offloading passengers from the incoming train
     *  @param incomingTrain the train that just arrived at the station
     */
    private void handleDisembarking(@NotNull Train incomingTrain) {
        this.disembarking += incomingTrain.getOffloadingPassengers();
    }

//    public Map<Integer, Integer> getBoarding () {  // TODO debatable whether this information should be accessible
//        return this.boarding;
//    }

    /**
     *  add passengers to the boarding queue
     *  @param passengers the number of passengers to add
     *  @return true if the passengers were successfully added, false if the station is closed
     */
    public boolean addBoarding (int ride_length, int passengers) {
        if (this.endOfDay) {
            return false;
        } else {
            this.boarding.put(ride_length, this.boarding.getOrDefault(ride_length, 0) + passengers);
            return true;
        }
    }

    public void setEndOfDay (boolean endOfDay) {
        this.endOfDay = endOfDay;
    }

    public Station (int distanceToNextNode, String name) {
        super(distanceToNextNode);
        this.name = name;
    }

    public Station (int distanceToNextNode, String name, @NotNull Node previousNode) {
        super(distanceToNextNode, previousNode);
        this.name = name;
    }

    public Station (int distanceToNextNode, String name, @NotNull Node previousNode, @NotNull Node nextNode) {
        super(distanceToNextNode, previousNode, nextNode);
        this.name = name;
    }
}
