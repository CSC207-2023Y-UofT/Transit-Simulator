package model.train.track;

import entity.model.control.TransitModel;
import entity.model.node.NodeFactory;
import entity.model.node.station.StationFactory;

import entity.model.train.track.NodeTrackSegment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;

public class NodeTrackSegmentTest {
    public static TransitModel transitModel;
    public static NodeFactory stationFactory;
    @DisplayName("NodeTrackSegmentTest Class Setup")
    @BeforeAll
    public static void setup() {
        transitModel = new TransitModel();
        stationFactory = new StationFactory();
    }

    @Test
    public void testConstructor() {
        NodeTrackSegment nodeTrackSegment = new NodeTrackSegment(transitModel.getTrackRepo(), stationFactory.createNode(transitModel, "station1"), "l1-s1-for", 100);
        Assertions.assertSame(transitModel.getTrackRepo(), nodeTrackSegment.getRepo());
        Assertions.assertEquals("l1-s1-for", nodeTrackSegment.getId());
        Assertions.assertEquals(100, nodeTrackSegment.getLength());
    }

    @Test
    public void testGetNode() {
        NodeTrackSegment nodeTrackSegment = new NodeTrackSegment(transitModel.getTrackRepo(), stationFactory.createNode(transitModel, "station1"), "l1-s1-for", 100);
        Assertions.assertTrue(nodeTrackSegment.getNode().isPresent());
        Assertions.assertEquals("station1", nodeTrackSegment.getNode().get().getName());
    }

    @DisplayName("NodeTrackSegmentTest Class Teardown")
    @AfterAll
    public static void teardown() {
        transitModel = null;
        stationFactory = null;
    }
}
