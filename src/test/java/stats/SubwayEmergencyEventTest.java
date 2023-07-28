package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stats.event.SubwayEmergencyEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for the SubwayEmergencyEvent class.
 */
public class SubwayEmergencyEventTest {

    private SubwayEmergencyEvent subwayEmergencyEvent;
    private final int testLine = 7;

    /**
     * Setup before each test case.
     */
    @BeforeEach
    public void setUp() {
        subwayEmergencyEvent = new SubwayEmergencyEvent(testLine);
    }

    /**
     * Test to check if the getLine method is working correctly.
     */
    @Test
    public void testGetLine() {
        assertEquals(testLine, subwayEmergencyEvent.getLine(),
                "The line number returned by getLine() did not match the expected line number.");
    }
}
