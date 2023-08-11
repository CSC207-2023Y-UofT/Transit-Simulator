package app_business.dto;

import org.junit.jupiter.api.*;

/**
 * The TrainArrivalDTOTest class contains tests for the TrainArrivalDTO class. A test for a data class.
 */
public class TrainArrivalDTOTest {
    static StationDTO stationDTO;
    static double distance;
    static TrainArrivalDTO trainArrivalDTO;

    @DisplayName("TrainArrivalDTOTest Class Setup")
    @BeforeAll
    static void setup() {
        stationDTO = new StationDTO("Station 99", null, 1, 2);
        distance = 99.99;
        trainArrivalDTO = new TrainArrivalDTO(stationDTO, distance);  // convenient test constructor
    }

    @Test
    void testGetStation() {
        Assertions.assertSame(stationDTO, trainArrivalDTO.getStation());
    }

    @Test
    void testGetDistance() {
        Assertions.assertEquals(distance, trainArrivalDTO.getDistance());
    }

    @DisplayName("TrainArrivalDTOTest Class Teardown")
    @AfterAll
    static void teardown() {
        stationDTO = null;
        distance = 0;
        trainArrivalDTO = null;
    }
}
