package main.pool;

import interface_adapter.controller.EmployeeController;
import interface_adapter.controller.StatsController;
import interface_adapter.controller.TicketController;
import interface_adapter.controller.TrainController;
import org.junit.jupiter.api.*;

/**
 * The ControllerPoolTest class contains tests for the ControllerPool class. A test for a data class.
 */
public class ControllerPoolTest {
    static ControllerPool controllerPool;
    static TrainController trainController;
    static TicketController ticketController;
    static EmployeeController employeeController;
    static StatsController statsController;
    @DisplayName("ControllerPoolTest Class Setup")
    @BeforeAll
    static void setup() {
        trainController = new TrainController(null);  // There is no reason to fill out the params; the object signature is enough.
        ticketController = new TicketController(null);
        employeeController = new EmployeeController(null);
        statsController = new StatsController(null);

        controllerPool = new ControllerPool(trainController, ticketController, employeeController, statsController);
    }

    @Test
    void testGetTrainController() {
        Assertions.assertSame(trainController, controllerPool.getTrainController());
    }

    @Test
    void testGetTicketController() {
        Assertions.assertSame(ticketController, controllerPool.getTicketController());
    }

    @Test
    void testGetEmployeeController() {
        Assertions.assertSame(employeeController, controllerPool.getEmployeeController());
    }

    @Test
    void testGetStatsController() {
        Assertions.assertSame(statsController, controllerPool.getStatController());
    }

    @DisplayName("ControllerPoolTest Class Teardown")
    @AfterAll
    static void teardown() {
        controllerPool = null;
        trainController = null;
        ticketController = null;
        employeeController = null;
        statsController = null;
    }
}
