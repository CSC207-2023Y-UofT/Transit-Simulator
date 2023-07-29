package stats;

import stats.aggregate.StatAggregate;
import stats.persistence.StatDataStore;
import stats.type.StatType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class StatRecorder<A extends StatAggregate<A>> {

    private final StatDataStore<A> dataStore;
    private final Map<StatType<?, A>, A> aggregates = new HashMap<>();

    public StatRecorder(StatDataStore<A> dataStore) {
        this.dataStore = dataStore;
    }

    public synchronized void flush() {
        long currentMinute = System.currentTimeMillis() / 60000;
        aggregates.forEach((type, aggregate) -> {
            dataStore.record(type, currentMinute, aggregate);
            aggregates.remove(type);
        });
    }

    public synchronized Optional<A> getCurrent(StatType<?, A> type) {
        return Optional.ofNullable(aggregates.get(type));
    }

    public synchronized void record(StatType<?, A> type, A aggregate) {
        if (aggregates.containsKey(type)) {
            aggregates.put(type, aggregates.get(type).merge(aggregate));
        } else {
            aggregates.put(type, aggregate);
        }
    }

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
