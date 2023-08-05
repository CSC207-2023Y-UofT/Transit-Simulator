package model.node;

/**
 * Interface for a factory that produces Node instances. This is used to abstract
 * the creation of Nodes, allowing for different types of Nodes to be created
 * without changing the calling code.
 */
public interface NodeFactory {  // Factory Method Design pattern used!!!

    /**
     * Creates a Node with the specified tracker, name, and length.
     *
     * @param transitTracker the NodeTracker that the new Node will be part of
     * @param name the name for the new Node
     * @return the newly created Node
     */
    Node createNode(NodeTracker transitTracker, String name);
}
