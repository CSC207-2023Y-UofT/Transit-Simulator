package model.node;

import model.train.TrackRepo;

import java.util.Map;

/**
 * The NodeTracker interface represents a collection of nodes in a track system.
 * It provides methods to interact with the nodes and the track repository.
 */
public interface NodeTracker {

    /**
     * Retrieves a map of all nodes in the track system.
     *
     * @return A map where the keys are the names of the nodes and the values are the Node objects.
     */
    Map<String, Node> getNodes();

    /**
     * Retrieves a specific node by its name.
     *
     * @param name The name of the node to retrieve.
     * @return The Node object with the specified name, or null if not found.
     */
    Node getNode(String name);

    /**
     * Creates a new node in the track system using the provided NodeFactory.
     *
     * @param factory The NodeFactory instance to use for creating the node.
     * @param name    The name of the new node.
     * @return The newly created Node object.
     */
    Node createNode(NodeFactory factory, String name);

    /**
     * Retrieves the TrackRepo associated with this NodeTracker.
     *
     * @return The TrackRepo instance containing information about the tracks in the system.
     */
    TrackRepo getTrackRepo();

}
