
/*
 *
 *
 *
 *
 */


import org.jetbrains.annotations.NotNull;  // This was automatically suggested by IntelliJ, idk what it does

public class Station extends Node {
    private int boarding = 0;  // passengers waiting for the next train

    public String name;


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
         *  5.5) Handle time
         *  6) Send train if online
         */
        super.receiveTrain(incomingTrain);  // 1), 1.5), 2) finished


    }

    public int getBoarding () {
        return this.boarding;
    }

    public void addBoarding (int passengers) {
        this.boarding += passengers;
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
