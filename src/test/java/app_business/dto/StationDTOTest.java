package app_business.dto;

import java.util.List;

import org.junit.jupiter.api.*;


/**
 * The StationDTOTest class contains tests for the StationDTO class. A test for a data class.
 */
public class StationDTOTest {
    static String name;
    static List<Integer> lines;
    static int x;
    static int y;
    static StationDTO stationDTO;

    @DisplayName("StationDTOTest Class Setup")
    @BeforeAll
    static void setup() {
        name = "Station 99";
        lines = List.of(1, 2, 3);
        x = 1;
        y = 2;
        stationDTO = new StationDTO(name, lines, x, y);  // convenient test constructor
    }

    @Test
    void testGetName() {
        Assertions.assertSame(name, stationDTO.getName());
    }

    @Test
    void testGetLines() {
        Assertions.assertSame(lines, stationDTO.getLines());
    }

    @Test
    void testGetX() {
        Assertions.assertSame(x, stationDTO.getX());
    }

    @Test
    void testGetY() {
        Assertions.assertSame(y, stationDTO.getY());
    }

    @DisplayName("StationDTOTest Class Teardown")
    @AfterAll
    static void teardown() {
        name = null;
        x = 0;
        y = 0;
        stationDTO = null;
    }

}
