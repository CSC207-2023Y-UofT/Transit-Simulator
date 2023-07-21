import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



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


    private List<Integer> directionCaseHelper(DirectionType direction, int numTrains, int distanceToOriginalStation) {
        if (direction == DirectionType.FORWARD) {
            return this.prev.nextArrivalsDistance(direction, numTrains, distanceToOriginalStation);
        } else {
            return this.next.nextArrivalsDistance(direction, numTrains, distanceToOriginalStation);
        }
    }

    /**
     *  This is a Node method that fills out a list of the distances of the next incoming trains.
     *  It recursively asks the previous or next Node for the next incoming trains, and stops when it has found the
     *  number of trains numTrains.
     *  @param direction the direction of the train
     *  @param numTrains the number of trains to find
     *  @param distanceToOriginalStation the distance to the original station
     *  @return a list of the distances of the next incoming trains
     */
    public List<Integer> nextArrivalsDistance (DirectionType direction, int numTrains, int distanceToOriginalStation) {  // TODO: Consider the corner case where there are no trains on the line. This program will run forever; we need to add a condition to stop the recursion.
        if (numTrains < 0) {
            throw new RuntimeException("numTrains must be non-negative");
        } else if (numTrains == 0) {
            return new ArrayList <> ();
        } else { // numTrains is larger than 0
            List<Train> correct_Trains = this.trains.stream().filter(train -> train.getDirection() == direction).collect(Collectors.toList());
            int num_correct_Trains = correct_Trains.size();
            if (num_correct_Trains == 0) {
                return this.directionCaseHelper(direction, numTrains, distanceToOriginalStation + this.distanceToNextNode);
            } else { // num_correct_Trains is larger than 0
                if (num_correct_Trains < numTrains) {
                    int remainder = numTrains - num_correct_Trains;
                    List<Integer> result = new ArrayList<>();
                    for (int i = 0; i < num_correct_Trains; i++) {
                        result.add(distanceToOriginalStation + correct_Trains.get(i).getDistanceToNextNode());
                    }
                    result.addAll(directionCaseHelper(direction, remainder, distanceToOriginalStation + this.distanceToNextNode));
                    return result;
                } else { // num_correct_Trains >= numTrains
                    List<Integer> result = new ArrayList<>();
                    for (int i = 0; i < numTrains; i++) {
                        result.add(distanceToOriginalStation + correct_Trains.get(i).getDistanceToNextNode());
                    }
                    return result;
                }
            }
        }
    }


    public int getDistanceToNextNode(Train train) {
        if (this.trains.contains(train)) {
            return this.distances.get(this.trains.indexOf(train));
        } else {
            throw new RuntimeException("That Train was not found in this node");
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

    public void updateTrainAndDistances() {
        // handle trains currently contained in this node
        for (int i = 0; i < this.trains.size(); i++) {
            if (this.trains.get(i).getStatus() == Train.StatusType.IN_SERVICE) {
                this.distances.set(i, this.distances.get(i) - 1); // decrement distance by 1
            }
            // Cannot handle sending trains here because we would be modifying the length of the list we are iterating
        }
        if (this.distances.get(0) <= 0) {  // NOTE: we always assume that there are never two trains at the same distance
            this.sendTrain(this.trains.get(0));
            this.trains.remove(0);
            this.distances.remove(0);
        }
    }

    public void setDistanceToNextNode(int distanceToNextNode) {
        this.distanceToNextNode = distanceToNextNode;
    }

    public int getDistanceToNextNode() {
        return this.distanceToNextNode;
    }

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
