package entity.model.node;

import entity.model.control.TransitModel;

import entity.model.node.line.NodeLineProfile;
import entity.model.node.station.Station;
import entity.model.node.station.StationFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;

public class NodeTest {
    public static TransitModel transitModel;
    public static StationFactory stationFactory;
    public static Node station1;
    public static Node station2;
    public static NodeLineProfile lineProfile1;

    @DisplayName("StationTest Class Setup")
    @BeforeAll
    public static void setup() {
        // Create the controller
        transitModel = new TransitModel();


        // Create the station factory
        stationFactory = new StationFactory();

        // Create the stations
        station1 = transitModel.createNode(stationFactory, "station1");
        station2 = transitModel.createNode(stationFactory, "station2");

        // Create a line profile for station 1
        lineProfile1 = station1.createLineProfile(1);
    }

    @Test
    public void testNode() {
        Node node1 = new Station(transitModel, "node1");
        Assertions.assertSame(node1.getName(), "node1");
        Assertions.assertSame(node1.getTracker(), transitModel);
    }

    @Test
    public void testGetTracker() {
        Assertions.assertSame(transitModel, station1.getTracker());
        Assertions.assertSame(transitModel, station2.getTracker());
    }

    @Test
    public void testGetName() {
        Assertions.assertSame("station1", station1.getName());
    }

    @Test
    public void testGetAndSetX() {
        station1.setX(1);
        Assertions.assertEquals(1, station1.getX());
    }

    @Test
    public void testGetAndSetY() {
        station1.setY(2);
        Assertions.assertEquals(2, station1.getY());
    }

    @Test
    public void testGetLineProfile() {
        Assertions.assertSame(lineProfile1, station1.getLineProfile(1).orElse(null));
        Assertions.assertNull(station1.getLineProfile(2).orElse(null));  // it doesn't exist
    }

    @Test
    public void testCreateLineProfileFirstPrecondition() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            station1.createLineProfile(0);  // line number must be positive
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            station1.createLineProfile(-2);  // line number must be positive
        });
    }

    @Test
    public void testCreateLineProfile() {
        NodeLineProfile lineProfile2 = station1.createLineProfile(2);
        Assertions.assertSame(lineProfile2, station1.getLineProfile(2).orElse(null));
    }

    @DisplayName("StationTest Class Teardown")
    @AfterAll
    public static void teardown() {  // for all the static variables
        transitModel = null;
        stationFactory = null;
        station1 = null;
        station2 = null;
        lineProfile1 = null;
    }
}
