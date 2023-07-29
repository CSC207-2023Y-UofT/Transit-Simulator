package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for the TrainUsageEvent class.
 */
public class TrainUsageEventTest {

    private TrainUsageEvent trainUsageEvent;
    private final int testLine = 8;

    /**
     * Setup before each test case.
     */
    @BeforeEach
    public void setUp() {
        trainUsageEvent = new TrainUsageEvent(testLine);
    }

    /**
     * Test to check if the getLine method is working correctly.
     */
    @Test
    public void testGetLine() {
        assertEquals(testLine, trainUsageEvent.getLine(),
                "The line number returned by getLine() did not match the expected line number.");
    }
}
