package model.train;

import ticket.Ticket;

/**
 * The Passenger class represents a passenger in a transportation system.
 * It acts as a placeholder class for storing passenger-related information.
 */
public class Passenger {
    /**
     * The ticket associated with the passenger.
     */
    private final Ticket ticket;
  
    /**
     * The number of stations to pass before the passenger gets off.
     * Precondition: Must be non-negative.
     */
    private int stationsToTravelPast;

    /**
     * Creates a passenger with the given number of stations to travel.
     * @return the number of stations to travel
     */
    public int getStationsToTravel() {
        return stationsToTravelPast;
    }

    /**
     * Sets the number of stations to travel.
     * @param stationsToTravelPast the number of stations to travel
     */
    public void setStationsToTravel(int stationsToTravelPast) {
        this.stationsToTravelPast = stationsToTravelPast;
    }

    /**
     * Decrements the number of stations to travel by 1.
     */
    public void decrementStationsToTravel() {
        this.stationsToTravelPast--;
    }

    /**
     * @return true if the passenger will alight at the next station
     */
    public boolean shouldAlight() {
        return stationsToTravelPast <= 0; // <= for safety
    }

    // Ticket functionality of Passengers
    /**
     * Constructs a new Passenger object with the specified ticket.
     *
     * @param ticket The Ticket object representing the passenger's ticket.
     */
    public Passenger(Ticket ticket, int stationsToTravel) {
        this.ticket = ticket;
        this.stationsToTravelPast = stationsToTravel;
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
