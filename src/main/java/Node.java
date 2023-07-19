import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;



/*
 *  TODO: Node Updater
 *  Potential idea: an updater that goes around
 *
 *
 *  On train arrival : controller action:
 *  1) Update the train's location
 *  2) change its location if this is an endpoint station
 *  3) Handle offloading passengers
 *  4) Handle statistics
 *  5) Make train offline if end of day, or scheduled for maintenance
 *  5.1) Handle onboarding passengers
 *  5.5) Handle time
 *  6) Send train if online
 *
 */



public abstract class Node {
    Node prev;
    Node next;
    int distanceToNextNode;
    List<Train> trains = new ArrayList<Train>();
    List<Integer> distances = new ArrayList<Integer>();

    // TODO: make a container to handle incoming trains and hold them

    // my family's news finally hit me days later. I never really
    // gonna take a break from this for a while.

    // gotta jot some notes down though.
    // Idea 1 BAD: dictionaries (1 for each direction) mapping train class to a number representing its current position
    // within the track, which would be equivalent information as the distance to the station (of course in the right
    // direction). Problems: We probably don't want to use class instances as dictionary keys, and it would probably be
    // tiring to change the values of every Train key every time the Plot is updated.

    // Idea 2 BAD: dictionaries, 1 for each direction. Mapping a key distance to each Train class. Problems: Probable
    // incredible use of space and memory when deleting old key and repointing a new key to a Train class.

    // Idea 3: Separate lists (a pair for each direction). One list for Trains, and one list for distances. Same
    // indexes share same information ie. Train at index zero has distance remaining to Station (at index zero of the
    // other list). The Train list is empty iff the Distance list is empty. Nice things: updating distance we can just
    // enumerate through the second list. Problems: I'm so fatigued and tired of life

    // However!! All of these has no way of handling Offline Trains. Maybe set their distance as (-1), or exclusively
    // keep them at Stations. NOTE: Train movement is only calculated when the Plot is updated, so we can just check
    // the corresponding Train's status and not move it if it's offline.

    // TODO: make a mechanism to handle the length (or time it takes) for a train to pass a simulated distance before
    //       sending it to the next node

    public void receiveTrain (Train incomingTrain) {
        this.trains.add(incomingTrain);
        this.distances.add(this.distanceToNextNode);
        incomingTrain.updateCurrentLocation(this);

        // check if this is an endpoint
        if (this.isForwardEndpoint() && this.isBackwardEndpoint()) {
            throw new RuntimeException("Singleton node has no way to move trains");
        } else if (this.isForwardEndpoint()) {  // TODO: consider using Train.changeDirection() instead
            if (incomingTrain.getDirection() == DirectionType.FORWARD) {
                incomingTrain.setDirection(DirectionType.BACKWARD);
            }
        } else if (this.isBackwardEndpoint()) {
            if (incomingTrain.getDirection() == DirectionType.BACKWARD) {
                incomingTrain.setDirection(DirectionType.FORWARD);
            }
        }
    }

    public void sendTrain (Train outgoingTrain) {
        // handle sending trains
        if (outgoingTrain.getDirection() == DirectionType.FORWARD) {
            this.next.receiveTrain(outgoingTrain);
        } else {
            this.prev.receiveTrain(outgoingTrain);
        }
    }

    private boolean isForwardEndpoint() {
        return this.next == null;
    }

    private boolean isBackwardEndpoint() {
        return this.prev == null;
    }

    public abstract void update();

    public Node (int distanceToNextNode) {
        this.distanceToNextNode = distanceToNextNode;
        this.prev = null;
        this.next = null;
    }

    public Node (int distanceToNextNode, @NotNull Node previousNode) {
        this.distanceToNextNode = distanceToNextNode;
        previousNode.next = this;
        this.prev = previousNode;
        this.next = null;
    }

    public Node (int distanceToNextNode, @NotNull Node previousNode, @NotNull Node nextNode) {
        this.distanceToNextNode = distanceToNextNode;
        previousNode.next = this;
        this.prev = previousNode;
        nextNode.prev = this;
        this.next = nextNode;
    }

}
