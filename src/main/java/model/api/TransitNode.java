package model.api;

import model.Direction;
import model.node.Node;

import java.util.Optional;

public interface TransitNode {
    String getName();
    Optional<TransitNodeLineProfile> getLineProfile(int lineNumber);
}
