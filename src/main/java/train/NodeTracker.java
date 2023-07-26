package train;

import java.util.List;
import java.util.Map;

public interface NodeTracker {
    Map<String, Node> getNodes();
    Node getNode(String name);
    Node createNode(NodeFactory factory, String name, double length);

}
