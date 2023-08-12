package app_business.interactor;

import app_business.dto.TrainDTO;
import entity.model.Direction;
import entity.model.control.TransitModel;
import entity.model.node.Node;
import entity.model.node.line.NodeLineProfile;
import entity.model.node.station.StationFactory;
import entity.model.train.Train;
import entity.model.train.TrainStatus;
import entity.model.train.track.TrackSegment;

import org.junit.jupiter.api.*;

public class TrainInteractorTest {
    TransitModel model;
    TrainInteractor interactor;
    @DisplayName("TrainInteractorTest Class SetUp")
    @BeforeEach
    public void setUp() {
        model = new TransitModel();
        interactor = new TrainInteractor(model);
    }

    @Test
    public void testFind() {
        Assertions.assertFalse(interactor.find("train1").isPresent());

        TrackSegment t1f = new TrackSegment(model.getTrackRepo(), "t1f", 100);
        model.getTrackRepo().addTrack(t1f);
        model.createTrain(t1f, "train1", 120);
        Assertions.assertTrue(interactor.find("train1").isPresent());
        Assertions.assertInstanceOf(TrainDTO.class, interactor.find("train1").get());
    }

    @Test
    public void testGetTrains() {
        Assertions.assertEquals(0, interactor.getTrains().size());

        TrackSegment t1f = new TrackSegment(model.getTrackRepo(), "t1f", 100);
        model.getTrackRepo().addTrack(t1f);
        model.createTrain(t1f, "train1", 120);
        Assertions.assertEquals(1, interactor.getTrains().size());

        TrackSegment t2f = new TrackSegment(model.getTrackRepo(), "t2f", 100);
        model.getTrackRepo().addTrack(t2f);
        model.createTrain(t2f, "train2", 120);
        Assertions.assertEquals(2, interactor.getTrains().size());
    }

    @Test
    public void testSetNeedsMaintenance() {
        interactor.setNeedsMaintenance("train1", true);

        TrackSegment t1f = new TrackSegment(model.getTrackRepo(), "t1f", 100);
        model.getTrackRepo().addTrack(t1f);
        model.createTrain(t1f, "train1", 120);

        Assertions.assertEquals(TrainStatus.OUT_OF_SERVICE, model.getTrain("train1").getStatus());
        interactor.setNeedsMaintenance("train1", true);
        Assertions.assertEquals(TrainStatus.UNDER_MAINTENANCE, model.getTrain("train1").getStatus());
        interactor.setNeedsMaintenance("train1", false);
        Assertions.assertEquals(TrainStatus.IN_SERVICE, model.getTrain("train1").getStatus());
    }

    @Test
    public void testToDTO() {
        // Scenario setup (other tests don't need this)
        StationFactory factory = new StationFactory();
        Node station1 = model.createNode(factory, "station1");  // Three stations linked in a line.
        Node station2 = model.createNode(factory, "station2");
        Node station3 = model.createNode(factory, "station3");
        station1.setX(100);
        station1.setY(150);
        station2.setX(200);
        station2.setY(250);
        station3.setX(300);
        station3.setY(350);
        NodeLineProfile l1s1 = station1.createLineProfile(1);
        NodeLineProfile l1s2 = station2.createLineProfile(1);
        NodeLineProfile l1s3 = station3.createLineProfile(1);
        TrackSegment s1f = l1s1.getTrack(Direction.FORWARD);
        TrackSegment s2f = l1s2.getTrack(Direction.FORWARD);
        TrackSegment s3f = l1s3.getTrack(Direction.FORWARD);
        TrackSegment s1b = l1s1.getTrack(Direction.BACKWARD);
        TrackSegment s2b = l1s2.getTrack(Direction.BACKWARD);
        TrackSegment s3b = l1s3.getTrack(Direction.BACKWARD);
        TrackSegment t1f = new TrackSegment(model.getTrackRepo(), "t1f", 100);
        TrackSegment t2f = new TrackSegment(model.getTrackRepo(), "t2f", 100);
        TrackSegment t1b = new TrackSegment(model.getTrackRepo(), "t1b", 100);
        TrackSegment t2b = new TrackSegment(model.getTrackRepo(), "t2b", 100);
        model.getTrackRepo().addTrack(t1f);
        model.getTrackRepo().addTrack(t2f);
        model.getTrackRepo().addTrack(t1b);
        model.getTrackRepo().addTrack(t2b);
        s1f.linkForward(t1f);
        t1f.linkForward(s2f);
        s2f.linkForward(t2f);
        t2f.linkForward(s3f);
        s3b.linkForward(t2b);
        t2b.linkForward(s2b);
        s2b.linkForward(t1b);
        t1b.linkForward(s1b);
        Train trainForwards = model.createTrain(s2f, "trainForwards", 120);
        trainForwards.setStatus(TrainStatus.IN_SERVICE);

        // Test
        TrainDTO dto = TrainInteractor.toDTO(trainForwards);
        Assertions.assertEquals("trainForwards", dto.getName());
        Assertions.assertEquals(120, dto.getCapacity());
        Assertions.assertEquals(0, dto.getOccupation());
        Assertions.assertTrue(dto.getCurrentStation().isPresent());
        Assertions.assertEquals("station2", dto.getCurrentStation().get().getName());
        Assertions.assertTrue(dto.getNextNodeDistance().isPresent());
        Assertions.assertEquals("station3", dto.getNextNodeDistance().get().getStation().getName());
        Assertions.assertEquals(200, dto.getNextNodeDistance().get().getDistance());  // 200 = 100 + 100
        Assertions.assertTrue(dto.getPreviousNodeDistance().isPresent());
        Assertions.assertEquals("station1", dto.getPreviousNodeDistance().get().getStation().getName());
        Assertions.assertEquals(100, dto.getPreviousNodeDistance().get().getDistance());
        Assertions.assertEquals(TrainStatus.IN_SERVICE, dto.getStatus());

    }
}







