package model.train;

public class Passenger {
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

    public Passenger(int stationsToTravel) {
        this.stationsToTravelPast = stationsToTravel;
    }

    public boolean willAlight() {
        return stationsToTravelPast <= 0; // <= for safety
    }
}
