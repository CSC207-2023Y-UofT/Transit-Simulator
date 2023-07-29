package stats;

import stats.aggregate.BasicStatAggregate;
import stats.aggregate.IndexedStatAggregate;
import stats.persistence.StatDataStore;
import stats.type.StatType;

/**
 * Class for tracking statistics.
 */
public class StatFacade implements StatRecorderFacade {
    private final StatRecorder<BasicStatAggregate> basicDataStore;
    private final StatRecorder<IndexedStatAggregate> indexedDataStore;

    public StatFacade(StatDataStore<BasicStatAggregate> basicDataStore, StatDataStore<IndexedStatAggregate> indexedDataStore) {
        this.basicDataStore = new StatRecorder<>(basicDataStore);
        this.indexedDataStore = new StatRecorder<>(indexedDataStore);
    }

    public StatRecorder<BasicStatAggregate> getBasicRecorder() {
        return basicDataStore;
    }

    public StatRecorder<IndexedStatAggregate> getIndexedRecorder() {
        return indexedDataStore;
    }

    @Override
    public <T> void record(StatType<T, ?> type, T entry) {
        type.process(this, entry);
    }

}
