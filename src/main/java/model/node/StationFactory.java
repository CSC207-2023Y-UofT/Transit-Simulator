package model.node;

public class StationFactory implements NodeFactory {
    @Override
    public Node createNode(NodeTracker transitTracker, String name) {
        return new Station(transitTracker, name);
    }
    }
