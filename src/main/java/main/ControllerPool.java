package main;

import controller.ticket.TicketController;

public class ControllerPool {
    private final TicketController ticketController;

    public ControllerPool(InteractorPool pool) {
        this.ticketController = new TicketController(pool.getTicketInteractor());
    }

    public TicketController getTicketController() {
        return ticketController;
    }
}
