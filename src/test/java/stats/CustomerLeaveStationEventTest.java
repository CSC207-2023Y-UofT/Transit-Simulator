package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stats.entry.CustomerLeaveStationEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for the CustomerLeaveStationEvent class.
 */
public class CustomerLeaveStationEventTest {

    private CustomerLeaveStationEvent customerLeaveStationEvent;
    private final String testStation = "Test Station";

    /**
     * Setup before each test case.
     */
    @BeforeEach
    public void setUp() {
        customerLeaveStationEvent = new CustomerLeaveStationEvent(testStation);
    }

    /**
     * Test to check if the getStation method is working correctly.
     */
    @Test
    public void testGetStation() {
        assertEquals(testStation, customerLeaveStationEvent.getStation(),
                "The station name returned by getStation() did not match the expected station name.");
    }
}
