package model.node;

/**
 * The StationFactory class is a factory that produces Station instances.
 */
public class StationFactory implements NodeFactory {
    /**
     * Creates a Station with the specified tracker and name.
     *
     * @param transitTracker the NodeTracker that the new Node will be part of
     * @param name           the name for the new Node
     * @return the newly created Node
     */
    @Override
    public Node createNode(NodeTracker transitTracker, String name) {
        return new Station(transitTracker, name);
    }
}
