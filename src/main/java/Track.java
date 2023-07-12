import org.jetbrains.annotations.NotNull;


/*
 *
 *  Representation Invariants:
 *  Every Track must point in between two Stations.
 */



public class Track extends Node implements ITrack{


    @Override
    public int nextArrivalDistance() {
        ;  // TODO: either implement this as a Station method with this as a helper method that recursively asks the
           //       previous tracks and stations for incoming trains (until it finds the closest one, adding up the
           //       distance of the previous passed tracks onto the result of the call of the stations closest to htat
           //       particular train) until the original station is asked (then we know there is no train and we
           //       cancel the method call)
    }

    public Track () {
        super();
    }

    public Track (@NotNull Node previousNode) {
        super(previousNode);
    }

    public Track (@NotNull Node previousNode, @NotNull Node nextNode) {
        super(previousNode, nextNode);
    }
}
