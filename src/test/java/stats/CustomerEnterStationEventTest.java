package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stats.event.CustomerEnterStationEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for the CustomerEnterStationEvent class.
 */
public class CustomerEnterStationEventTest {

    private CustomerEnterStationEvent customerEnterStationEvent;
    private final String testStation = "Test Station";

    /**
     * Setup before each test case.
     */
    @BeforeEach
    public void setUp() {
        customerEnterStationEvent = new CustomerEnterStationEvent(testStation);
    }

    /**
     * Test to check if the getStation method is working correctly.
     */
    @Test
    public void testGetStation() {
        assertEquals(testStation, customerEnterStationEvent.getStation(),
                "The station name returned by getStation() did not match the expected station name.");
    }
}
