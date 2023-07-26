package train;

import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TransitTracker implements NodeTracker, TrainTracker {

    private final List<Train> trainList = new ArrayList<>();
    private final Map<String, Node> nodeMap = new HashMap<>();

    public Train createTrain(String nodeId, Direction direction, int capacity) {
        if (!nodeMap.containsKey(nodeId)) {
            throw new IllegalArgumentException("Node " + nodeId + " does not exist");
        }

        Node node = nodeMap.get(nodeId);

        double positionAlongNode = direction == Direction.FORWARD ? 0 : node.getLength();

        TrainPosition position = new TrainPosition(node, positionAlongNode);

        Train train = new Train(this, direction, position, capacity);
        trainList.add(train);

        return train;
    }

    @Override
    public Map<String, Node> getNodes() {
        return Collections.unmodifiableMap(nodeMap);
    }

    @Override
    public @Nullable Node getNode(String name) {
        return nodeMap.get(name);
    }

    public Node createNode(NodeFactory nodeFactory, String identifier, double length) {
        Node node = nodeFactory.createNode(this, identifier, length);
        if (node.getTracker() != this) {
            throw new IllegalArgumentException("Node " + identifier + " created with wrong tracker");
        }
        nodeMap.put(identifier, node);
        return node;
    }

}
