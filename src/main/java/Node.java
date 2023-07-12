import org.jetbrains.annotations.NotNull;


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
    // TODO: make a container to handle incoming trains and hold them

    // my family's news finally hit me days later. I never really
    // gonna take a break from this for a while.

    // gotta jot some notes down though.
    // Idea 1: dictionaries (1 for each direction) mapping train class to a number representing its current position
    // within the track, which would be equivalent information as the distance to the station (of course in the right
    // direction). Problems: We probably don't want to use class instances as dictionary keys, and it would probably be
    // tiring to change the values of every Train key every time the Plot is updated.

    // Idea 2: dictionaries, 1 for each direction. Mapping a key distance to each Train class. Problems: Probable
    // incredible use of space and memory when deleting old key and repointing a new key to a Train class.

    // Idea 3: Separate lists (a pair for each direction). One list for Trains, and one list ofr distances. Same
    // indexes share same information ie. Train at index zero has distance remaining to Station (at index zero of the
    // other list). The Train list is empty iff the Distance list is empty. Nice things: updating distance we can just
    // enumerate through the second list. Problems: I'm so fatigued and tired of life

    // However!! All of these has no way of handling Offline Trains. Maybe set their distance as (-1), or exclusively
    // keep them at Stations.

    // TODO: make a mechanism to handle the length (or time it takes) for a train to pass a simulated distance before
    //       sending it to the next node

    private void receiveTrain (Train incomingTrain) {}  // TODO

    private void sendTrain () {}  // TODO?


    public Node () {
        this.prev = null;
        this.next = null;
    }

    public Node (@NotNull Node previousNode) {
        previousNode.next = this;
        this.prev = previousNode;
        this.next = null;
    }

    public Node (@NotNull Node previousNode, @NotNull Node nextNode) {
        previousNode.next = this;
        this.prev = previousNode;
        nextNode.prev = this;
        this.next = nextNode;
    }
}
