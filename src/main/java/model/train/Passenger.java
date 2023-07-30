package model.train;

import ticket.Ticket;

/**
 * The Passenger class represents a passenger in a transportation system.
 * It acts as a placeholder class for storing passenger-related information.
 */
public class Passenger {

    private final Ticket ticket;

    /**
     * Constructs a new Passenger object with the specified ticket.
     *
     * @param ticket The Ticket object representing the passenger's ticket.
     */
    public Passenger(Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * Retrieves the Ticket object associated with the passenger.
     *
     * @return The Ticket object representing the passenger's ticket.
     */
    public Ticket getTicket() {
        return ticket;
    }

}
