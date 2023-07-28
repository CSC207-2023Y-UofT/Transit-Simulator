package stats;

import stats.aggregator.old.CustomerEnterStationAggregator;
import stats.event.CustomerEnterStationEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface for StatEntry to be Serializable.
 */
public interface StatEntry extends Serializable {
    static void main(String[] args) {
        List<StatEntry> events = new ArrayList<>();
        for (int i = 0; i < 430000; i++) {
            events.add(new CustomerEnterStationEvent("station" + i));
        }

        CustomerEnterStationAggregator aggregator = new CustomerEnterStationAggregator();
        long time = System.currentTimeMillis();
        aggregator.aggregate(events);
        time = System.currentTimeMillis() - time;
        System.out.println("Time: " + time);
        System.out.println("Entries: " + aggregator.getEntries("station0"));

    }
}