package interface_adapter.controller;

import app_business.boundary.ITrainInteractor;
import app_business.dto.TrainDTO;

import java.util.List;
import java.util.Optional;

import entity.model.train.TrainStatus;
import org.junit.jupiter.api.*;

/**
 * Tests for the TrainController class. Take note of the mocking of the ITrainInteractor interface; this is for
 * isolating the behavior of the interactor from the controller, since we are testing only the controller.
 * Actual testing for interactors is done in their own test classes.
 */
public class TrainControllerTest {
    MockTrainInteractor mockTrainInteractor;
    TrainController trainController;

    @DisplayName("TrainControllerTest Class Setup")
    @BeforeEach  // to avoid tests depending on each other
    public void setup() {
        mockTrainInteractor = new MockTrainInteractor();
        trainController = new TrainController(mockTrainInteractor);
    }

    @Test
    public void testFind_ExistingTrain() {
        Optional<TrainDTO> train = trainController.find("Train 99");

        Assertions.assertTrue(train.isPresent());
        Assertions.assertEquals("Train 99", train.get().getName());
    }

    @Test
    public void testFind_NonExistentTrain() {
        Optional<TrainDTO> train = trainController.find("Train 100");

        Assertions.assertFalse(train.isPresent());
    }

    @Test
    public void testFindAll() {
        List<TrainDTO> trains = trainController.findAll();

        Assertions.assertEquals(2, trains.size());
    }

    @Test
    public void testSetNeedsMaintenance() {
        trainController.setNeedsMaintenance("Train 96", true);

        Assertions.assertTrue(mockTrainInteractor.wasMaintenanceUpdated());
    }



    /**
     * The MockTrainInteractor provides hardcoded behavior for the ITrainInteractor methods to assist in isolating the
     * tests of TrainController.
     */
    private class MockTrainInteractor implements ITrainInteractor {
        boolean maintenanceUpdated = false;
        @Override
        public Optional<TrainDTO> find(String name) {
            if ("Train 99".equals(name)) {
                return Optional.of(new TrainDTO("Train 99", 120, 100, null, null, null, TrainStatus.IN_SERVICE));
            }
            return Optional.empty();
        }

        @Override
        public List<TrainDTO> getTrains() {
            return List.of(
                    new TrainDTO("Train 98", 120, 100, null, null, null, TrainStatus.IN_SERVICE),
                    new TrainDTO("Train 97", 120, 100, null, null, null, TrainStatus.IN_SERVICE)
            );
        }

        @Override
        public void setNeedsMaintenance(String trainName, boolean needsMaintenance) {  // basic way of verifying behavior when method doesn't return anything
            maintenanceUpdated = true;  // Only record that the method was called
        }

        public boolean wasMaintenanceUpdated() {
            return maintenanceUpdated;
        }
    }
}
