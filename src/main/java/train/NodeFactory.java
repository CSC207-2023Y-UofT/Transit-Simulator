package train;

public interface NodeFactory {
    Node createNode(NodeTracker transitTracker, String name, double length);
}
