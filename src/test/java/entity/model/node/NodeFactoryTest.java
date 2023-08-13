package entity.model.node;

import entity.model.control.TransitModel;

import entity.model.node.station.Station;
import entity.model.node.station.StationFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;


public class NodeFactoryTest {
    public static NodeFactory nodeFactory;
    @DisplayName("NodeFactoryTest Class Setup")
    @BeforeAll
    public static void setup() {
        nodeFactory = new StationFactory();
    }

    @Test
    public void testCreateNode() {
        NodeTracker nodeTracker = new TransitModel();
        Node node = nodeFactory.createNode(nodeTracker, "test");
        Assertions.assertInstanceOf(Station.class, node);
        Assertions.assertEquals(nodeTracker, node.getTracker());
        Assertions.assertEquals("test", node.getName());
    }

    @DisplayName("NodeFactoryTest Class Teardown")
    @AfterAll
    public static void teardown() {
        nodeFactory = null;
    }
}
