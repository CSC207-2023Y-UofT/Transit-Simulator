package model.api;

import model.Direction;
import model.node.Node;
import model.node.TrainArrival;

import java.util.List;
import java.util.Optional;

public interface TransitNodeLineProfile {
    Optional<Node> getNextNode(Direction direction);
    int getLineNumber();
}
