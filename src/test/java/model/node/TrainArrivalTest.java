package model.node;

import model.control.TransitModel;
import model.train.Train;
import model.Direction;

import model.train.track.TrackSegment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;

// This class is simply a data class, so there is little to test.
public class TrainArrivalTest {
    public static TransitModel transitModel;
    public static StationFactory stationFactory;
    public static Node station1;
    public static NodeLineProfile lineProfile1;
    public static Train train;
    @DisplayName("TrainArrivalTest Class Setup")
    @BeforeAll
    public static void setup() {
        transitModel = new TransitModel();
        stationFactory = new StationFactory();
        station1 = transitModel.createNode(stationFactory, "station1");

        lineProfile1 = station1.createLineProfile(1);
        TrackSegment s1f = lineProfile1.getTrack(Direction.FORWARD);

        train = transitModel.createTrain(s1f, "t3", 120);
    }

    @Test
    public void testAll() {
        TrainArrival arrival = new TrainArrival(train, station1, 100);
        Assertions.assertInstanceOf(Train.class, arrival.getTrain());
        Assertions.assertEquals(train, arrival.getTrain());
        Assertions.assertInstanceOf(Node.class, arrival.getNode());
        Assertions.assertEquals(station1, arrival.getNode());
        Assertions.assertInstanceOf(Long.class, arrival.getDelay());
        Assertions.assertEquals(100, arrival.getDelay());
    }

    @DisplayName("TrainArrivalTest Class Teardown")
    @AfterAll
    public static void teardown() {
        transitModel = null;
        stationFactory = null;
        station1 = null;
        lineProfile1 = null;
        train = null;
    }
}
