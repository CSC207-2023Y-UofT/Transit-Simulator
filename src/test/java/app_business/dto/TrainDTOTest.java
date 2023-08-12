package app_business.dto;

import entity.model.train.TrainStatus;

import org.junit.jupiter.api.*;

/**
 * The TrainDTOTest class contains tests for the TrainDTO class. A test for a data class.
 */
public class TrainDTOTest {
    static String name;
    static int capacity;
    static int occupation;
    static StationDTO currentStation;
    static TrainArrivalDTO nextNodeDistance;
    static TrainArrivalDTO previousNodeDistance;
    static TrainStatus status;
    static TrainDTO trainDTO;

    @DisplayName("TrainDTOTest Class Setup")
    @BeforeAll
    static void setup() {
        name = "Train 99";
        capacity = 120;
        occupation = 99;
        currentStation = new StationDTO("Station 99", null, 1, 2);
        nextNodeDistance = new TrainArrivalDTO(currentStation, 99.99);
        previousNodeDistance = new TrainArrivalDTO(currentStation, 99.99);
        status = TrainStatus.IN_SERVICE;
        trainDTO = new TrainDTO(name, capacity, occupation, currentStation, nextNodeDistance, previousNodeDistance, status);  // convenient test constructor
    }

    @Test
    void testGetStatus() {
        Assertions.assertSame(status, trainDTO.getStatus());
    }

    @Test
    void testGetName() {
        Assertions.assertEquals(name, trainDTO.getName());
    }

    @Test
    void testGetCapacity() {
        Assertions.assertEquals(capacity, trainDTO.getCapacity());
    }

    @Test
    void testGetOccupation() {
        Assertions.assertEquals(occupation, trainDTO.getOccupation());
    }

    @Test
    void testGetCurrentStation() {
        Assertions.assertTrue(trainDTO.getCurrentStation().isPresent());
        Assertions.assertSame(currentStation, trainDTO.getCurrentStation().get());
    }

    @Test
    void testGetNextNodeDistance() {
        Assertions.assertTrue(trainDTO.getNextNodeDistance().isPresent());
        Assertions.assertSame(nextNodeDistance, trainDTO.getNextNodeDistance().get());
    }

    @Test
    void testGetPreviousNodeDistance() {
        Assertions.assertTrue(trainDTO.getPreviousNodeDistance().isPresent());
        Assertions.assertSame(previousNodeDistance, trainDTO.getPreviousNodeDistance().get());
    }

    @DisplayName("TrainDTOTest Class Teardown")
    @AfterAll
    static void teardown() {
        name = null;
        capacity = 0;
        occupation = 0;
        currentStation = null;
        nextNodeDistance = null;
        previousNodeDistance = null;
        status = null;
        trainDTO = null;
    }
}
