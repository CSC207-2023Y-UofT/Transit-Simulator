package app_business.interactor;

import app_business.dto.TrainDTO;
import entity.model.control.TransitModel;
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

    @Disabled
    @Test
    public void testToDTO() {
        // TODO: Implement this test.
    }
}







