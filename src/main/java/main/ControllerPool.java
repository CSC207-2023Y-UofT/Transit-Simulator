package main;

import controller.stats.StatsController;
import controller.ticket.TicketController;

/**
 * This class acts as a centralized repository for all controllers in the system.
 * By using the InteractorPool, the ControllerPool initializes all the controllers and provides access methods to them.
 */
public class ControllerPool {

    /** The ticket-related controller. */
    private final TicketController ticketController;

    /** The stats-related controller. */
    private final StatsController statController;

    /**
     * Constructs a new ControllerPool using the provided InteractorPool.
     * Initializes the ticket and stats controllers.
     *
     * @param pool The InteractorPool used for creating the controllers.
     */
    public ControllerPool(InteractorPool pool) {
        this.ticketController = new TicketController(pool.getTicketInteractor());
        statController = new StatsController(pool.getStatInteractor());
    }

    /**
     * Provides access to the TicketController.
     *
     * @return The TicketController instance.
     */
    public TicketController getTicketController() {
        return ticketController;
    }

    /**
     * Provides access to the StatsController.
     *
     * @return The StatsController instance.
     */
    public StatsController getStatController() {
        return statController;
    }
}
