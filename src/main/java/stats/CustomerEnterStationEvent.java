package stats;

public class CustomerEnterStationEvent implements StatEntry {


    private final String station;

    public CustomerEnterStationEvent(String station) {
        this.station = station;
    }

    public String getStation() {
        return station;
    }

}
