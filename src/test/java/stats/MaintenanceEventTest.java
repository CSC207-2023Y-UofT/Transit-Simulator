package stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stats.entry.MaintenanceEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for the MaintenanceEvent class.
 */
public class MaintenanceEventTest {

    private MaintenanceEvent maintenanceEvent;
    private final double testCost = 500.00;
    private final double delta = 0.001;  // a small delta to compare double values

    /**
     * Setup before each test case.
     */
    @BeforeEach
    public void setUp() {
        maintenanceEvent = new MaintenanceEvent(testCost);
    }

    /**
     * Test to check if the getExpense method is working correctly.
     */
    @Test
    public void testGetExpense() {
        assertEquals(testCost, maintenanceEvent.getExpense(), delta,
                "The cost returned by getExpense() did not match the expected cost.");
    }
}
