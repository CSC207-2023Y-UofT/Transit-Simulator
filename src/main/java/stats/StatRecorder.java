package stats;

import stats.aggregate.StatAggregate;
import stats.persistence.StatDataStore;
import stats.type.StatType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Class responsible for recording and retrieving statistical data.
 * It holds a collection of StatType and StatAggregate pairs for managing statistical data.
 */
 public class StatRecorder<A extends StatAggregate<A>> {

    /**
     * A StatDataStore instance used for storing statistical data.
     */
    private final StatDataStore<A> dataStore;

    /**
     * A map holding StatType objects and corresponding StatAggregate data.
     */
    private final Map<StatType<?, A>, A> aggregates = new HashMap<>();

    /**
     * Constructor for the StatRecorder class.
     *
     * @param dataStore a StatDataStore instance used for storing statistical data.
     */
    public StatRecorder(StatDataStore<A> dataStore) {
        this.dataStore = dataStore;
    }

    /**
     * Synchronised method to flush (i.e., record and remove) the current aggregate statistics to the data store.
     * Each aggregate data is associated with a timestamp (in minutes).
     */
    public synchronized void flush() {
        long currentMinute = System.currentTimeMillis() / 60000;
        aggregates.forEach((type, aggregate) -> {
            dataStore.record(type, currentMinute, aggregate);
            aggregates.remove(type);
        });
    }

    /**
     * Synchronised method to retrieve the current StatAggregate associated with the provided StatType.
     *
     * @param type the StatType whose associated StatAggregate is to be retrieved.
     * @return an Optional containing the StatAggregate associated with the provided StatType, if it exists.
     */
    public synchronized Optional<A> getCurrent(StatType<?, A> type) {
        return Optional.ofNullable(aggregates.get(type));
    }

    /**
     * Synchronised method to record a StatAggregate associated with a StatType.
     * If a StatAggregate already exists for the provided StatType, the new StatAggregate is merged with it.
     * Otherwise, the new StatAggregate is added to the collection.
     *
     * @param type the StatType associated with the provided StatAggregate.
     * @param aggregate the StatAggregate to be recorded.
     */
    public synchronized void record(StatType<?, A> type, A aggregate) {
        if (aggregates.containsKey(type)) {
            aggregates.put(type, aggregates.get(type).merge(aggregate));
        } else {
            aggregates.put(type, aggregate);
        }
    }

    /**
     * Method to create a report for a specified StatType between a given start and end minute.
     * The report is created asynchronously and a CompletableFuture is returned.
     *
     * @param type the StatType for which the report is to be created.
     * @param startMinute the start minute for the report.
     * @param endMinute the end minute for the report.
     * @return a CompletableFuture that will complete with the StatReport.
     */
    public CompletableFuture<StatReport<A>> getReport(
            StatType<?, A> type,
            long startMinute,
            long endMinute
    ) {
        Optional<A> current = getCurrent(type);
        long minute = System.currentTimeMillis() / 60000;

        return dataStore.getReport(type, startMinute, endMinute)
                .thenApply(report -> {
                    current.ifPresent(currData -> report.getData().put(minute, currData));
                    return report;
                });
    }

}
