package stats;


public class CustomerLeaveStationEvent implements StatEntry{

    private final String station;

    public CustomerLeaveStationEvent(String station) {
        this.station = station;
    }

    public String getStation() {return station;}

}
