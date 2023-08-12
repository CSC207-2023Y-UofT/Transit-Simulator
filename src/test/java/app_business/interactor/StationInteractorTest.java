package app_business.interactor;

import app_business.dto.StationDTO;
import entity.model.Direction;
import entity.model.control.TransitModel;
import entity.model.node.Node;
import entity.model.node.line.NodeLineProfile;
import entity.model.node.station.StationFactory;
import entity.model.train.repo.TrackRepo;
import entity.model.train.track.TrackSegment;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;

public class StationInteractorTest {
    TransitModel model;
    TrackRepo repo;
    StationFactory factory;
    Node station1;
    Node station2;
    Node station3;
    StationInteractor interactor;
    @BeforeEach
    public void setUp() {
        model = new TransitModel();
        factory = new StationFactory();
        repo = model.getTrackRepo();

        station1 = model.createNode(factory, "station1");  // Three stations linked in a line.
        station1.setX(100);
        station1.setY(150);
        station2 = model.createNode(factory, "station2");
        station2.setX(200);
        station2.setY(250);
        station3 = model.createNode(factory, "station3");
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
        TrackSegment t1f = new TrackSegment(repo, "l1-s1-for", 100);
        TrackSegment t2f = new TrackSegment(repo, "l1-s2-for", 100);
        TrackSegment t1b = new TrackSegment(repo, "l1-s1-back", 100);
        TrackSegment t2b = new TrackSegment(repo, "l1-s2-back", 100);
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

        interactor = new StationInteractor(model);
    }

    @Test
    public void testFind() {
        Assertions.assertTrue(interactor.find("station1").isPresent());
        Assertions.assertEquals("station1", interactor.find("station1").get().getName());
        Assertions.assertEquals(1, interactor.find("station1").get().getLines().size());
        Assertions.assertEquals(100, interactor.find("station1").get().getX());
        Assertions.assertEquals(150, interactor.find("station1").get().getY());
        Assertions.assertFalse(interactor.find("station4").isPresent());
    }

    @Test
    public void testGetNextStation() {
        Optional<StationDTO> testVariable_1 = interactor.getNextStation("station1", 1, Direction.FORWARD);
        Assertions.assertTrue(testVariable_1.isPresent());
        Assertions.assertEquals("station2", testVariable_1.get().getName());
        Assertions.assertEquals(1, testVariable_1.get().getLines().size());
        Assertions.assertEquals(200, testVariable_1.get().getX());
        Assertions.assertEquals(250, testVariable_1.get().getY());

        Optional<StationDTO> testVariable_2 = interactor.getNextStation("station3", 1, Direction.FORWARD);
        Assertions.assertFalse(testVariable_2.isPresent());

        Optional<StationDTO> testVariable_3 = interactor.getNextStation("station4", 1, Direction.FORWARD);
        Assertions.assertFalse(testVariable_3.isPresent());
    }

    @Test
    public void testGetStations() {
        List<StationDTO> testVariable_1 = interactor.getStations();
        Assertions.assertEquals(3, testVariable_1.size());
        for (String stationName : List.of("station1", "station2", "station3")) {  // Check if all stations are present
            Assertions.assertEquals(stationName, testVariable_1.stream()
                    .filter(it -> it.getName().equals(stationName))
                    .findFirst()
                    .orElseThrow()
                    .getName());
        }

        // Now testing empty case
        model = new TransitModel();
        interactor = new StationInteractor(model);
        List<StationDTO> testVariable_2 = interactor.getStations();
        Assertions.assertEquals(0, testVariable_2.size());
    }

    @Test
    public void testGetTimeTillNextArrival() {
        Assertions.assertFalse(interactor.getTimeTillNextArrival("station4", 1, Direction.FORWARD).isPresent());

        Assertions.assertFalse(interactor.getTimeTillNextArrival("station2", 1, Direction.FORWARD).isPresent());

        Assertions.assertTrue(station1.getLineProfile(1).isPresent());  // only needed for java warning Optional.get()
        model.createTrain(station1.getLineProfile(1).get().getTrack(Direction.FORWARD), "trainForwards", 120);

        Assertions.assertTrue(interactor.getTimeTillNextArrival("station3", 1, Direction.FORWARD).isPresent());
    }

    @Test
    public void testToDTO() {
        StationDTO testVariable_1 = StationInteractor.toDTO(station1);
        Assertions.assertEquals("station1", testVariable_1.getName());
        Assertions.assertEquals(1, testVariable_1.getLines().size());
        Assertions.assertEquals(1, testVariable_1.getLines().get(0));
        Assertions.assertEquals(100, testVariable_1.getX());
        Assertions.assertEquals(150, testVariable_1.getY());
    }
}