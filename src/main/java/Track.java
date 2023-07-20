import org.jetbrains.annotations.NotNull;

import java.util.List;


/*
 *
 *  Representation Invariants:
 *  Every Track must point in between two Stations.
 */



public class Track extends Node {


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
