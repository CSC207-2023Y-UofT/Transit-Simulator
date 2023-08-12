package main.pool;

import app_business.interactor.*;
import org.junit.jupiter.api.*;

/**
 * The InteractorPoolTest class contains tests for the InteractorPool class. A test for a data class.
 */
public class InteractorPoolTest {
    static InteractorPool interactorPool;
    static StationInteractor stationInteractor;
    static TrainInteractor trainInteractor;
    static TicketInteractor ticketInteractor;
    static EmployeeInteractor employeeInteractor;
    static StatInteractor statsInteractor;

    @DisplayName("InteractorPoolTest Class Setup")
    @BeforeAll
    static void setup() {
        stationInteractor = new StationInteractor(null);  // the object signature is enough, we don't need params
        trainInteractor = new TrainInteractor(null);
        ticketInteractor = new TicketInteractor(null, null);
        employeeInteractor = new EmployeeInteractor(null, null);
        statsInteractor = new StatInteractor(null);

        interactorPool = new InteractorPool(stationInteractor, trainInteractor, ticketInteractor ,employeeInteractor, statsInteractor);
    }

    @Test
    void testGetStationInteractor() {
        Assertions.assertSame(stationInteractor, interactorPool.getStationInteractor());
    }

    @Test
    void testGetTrainInteractor() {
        Assertions.assertSame(trainInteractor, interactorPool.getTrainInteractor());
    }

    @Test
    void testGetTicketInteractor() {
        Assertions.assertSame(ticketInteractor, interactorPool.getTicketInteractor());
    }

    @Test
    void testGetEmployeeInteractor() {
        Assertions.assertSame(employeeInteractor, interactorPool.getEmployeeInteractor());
    }

    @Test
    void testGetStatsInteractor() {
        Assertions.assertSame(statsInteractor, interactorPool.getStatInteractor());
    }

    @DisplayName("InteractorPoolTest Class Teardown")
    @AfterAll
    static void teardown() {
        interactorPool = null;
        stationInteractor = null;
        trainInteractor = null;
        ticketInteractor = null;
        employeeInteractor = null;
        statsInteractor = null;
    }
}
