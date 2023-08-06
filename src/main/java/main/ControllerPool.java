package main;

import controller.stats.StatsController;
import controller.ticket.TicketController;

public class ControllerPool {
    private final TicketController ticketController;
    private final StatsController statController;

    public ControllerPool(InteractorPool pool) {
        this.ticketController = new TicketController(pool.getTicketInteractor());
        statController = new StatsController(pool.getStatInteractor());
    }

    public TicketController getTicketController() {
        return ticketController;
    }

    public StatsController getStatController() {
        return statController;
    }
}
