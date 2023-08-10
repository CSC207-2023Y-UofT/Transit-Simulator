package main.pool;

import interface_adapter.employee.EmployeeController;
import interface_adapter.stats.StatsController;
import interface_adapter.ticket.TicketController;
import interface_adapter.train.TrainController;

/**
 * The ControllerPool dataclass represents a collection of a ticketController and a statController.
 */
public class ControllerPool {

    /**
     * The ticket controller.
     */
    private final TicketController ticketController;

    /**
     * The stat controller.
     */
    private final StatsController statController;

    /**
     * The employee controller.
     */
    private final EmployeeController employeeController;

    /**
     * The train controller.
     */
    private final TrainController trainController;

    /**
     * Constructs a new ControllerPool with the given ticket and stat controllers.
     *
     * @param trainController The train controller.
     * @param ticketController The ticket controller.
     * @param employeeController The employee controller.
     * @param statController The stat controller.
     */
    public ControllerPool(TrainController trainController, TicketController ticketController, EmployeeController employeeController, StatsController statController) {  // Changed because of dependency injection. Now we can directly inject params in tests.
        this.trainController = trainController;
        this.ticketController = ticketController;
        this.employeeController = employeeController;
        this.statController = statController;
    }

    /**
     * Gets the ticket controller.
     *
     * @return The ticket controller.
     */
    public TicketController getTicketController() {
        return ticketController;
    }

    /**
     * Gets the stat controller.
     *
     * @return The stat controller.
     */
    public StatsController getStatController() {
        return statController;
    }

    /**
     * Gets the employee controller.
     */
    public EmployeeController getEmployeeController() {
        return employeeController;
    }

    /**
     * Gets the train controller.
     */
    public TrainController getTrainController() {
        return trainController;
    }
}
