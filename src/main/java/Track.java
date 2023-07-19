import org.jetbrains.annotations.NotNull;


/*
 *
 *  Representation Invariants:
 *  Every Track must point in between two Stations.
 */



public class Track extends Node implements ITrack{



    @Override
    public void update() {
        // handle trains currently on the track
        for (int i = 0; i < this.trains.size(); i++) {
            if (this.trains.get(i).getStatus() == Train.StatusType.IN_SERVICE) {
                this.distances.set(i, this.distances.get(i) - 1); // decrement distance by 1
            }
            // Cannot handle sending trains here because we would be modifying the length of the list we are iterating
        }
        if (this.distances.get(0) == 0) {
            this.sendTrain(this.trains.get(0));
            this.trains.remove(0);
            this.distances.remove(0);
        }
    }

    @Override
    public int nextArrivalDistance() {

        ;  // TODO: either implement this as a Station method with this as a helper method that recursively asks the
           //       previous tracks and stations for incoming trains (until it finds the closest one, adding up the
           //       distance of the previous passed tracks onto the result of the call of the stations closest to htat
           //       particular train) until the original station is asked (then we know there is no train and we
           //       cancel the method call)
    }

    public Track (int distanceToNextNode) {
        super(distanceToNextNode);
    }

    public Track (int distanceToNextNode, @NotNull Node previousNode) {
        super(distanceToNextNode, previousNode);
    }

    public Track (int distanceToNextNode, @NotNull Node previousNode, @NotNull Node nextNode) {
        super(distanceToNextNode, previousNode, nextNode);
    }
}
