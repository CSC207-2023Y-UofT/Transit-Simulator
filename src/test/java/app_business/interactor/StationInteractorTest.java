package app_business.interactor;

import entity.model.control.TransitModel;
import entity.model.train.repo.TrackRepo;

import org.junit.jupiter.api.*;

public class StationInteractorTest {
    TransitModel model;
    TrackRepo repo;
    StationInteractor interactor;
    @BeforeEach
    public void setUp() {
        model = new TransitModel();
        repo = model.getTrackRepo();
        interactor = new StationInteractor(model);
    }

    @Test
    public void testFind() {
    }

    @Test
    public void testGetNextStation() {
    }

    @Test
    public void testGetStations() {
    }

    @Test
    public void testGetTimeTillNextArrival() {
    }

    @Test
    public void testToDTO() {
    }
}