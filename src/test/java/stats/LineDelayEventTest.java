package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stats.event.LineDelayEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for the LineDelayEvent class.
 */
public class LineDelayEventTest {

    private LineDelayEvent lineDelayEvent;
    private final int testLine = 5;

    /**
     * Setup before each test case.
     */
    @BeforeEach
    public void setUp() {
        lineDelayEvent = new LineDelayEvent(testLine);
    }

    /**
     * Test to check if the getLine method is working correctly.
     */
    @Test
    public void testGetLine() {
        assertEquals(testLine, lineDelayEvent.getLine(),
                "The line number returned by getLine() did not match the expected line number.");
    }
}
