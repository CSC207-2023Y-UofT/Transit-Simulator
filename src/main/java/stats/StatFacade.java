package stats;

import stats.aggregate.BasicStatAggregate;
import stats.aggregate.IndexedStatAggregate;
import stats.persistence.StatDataStore;
import stats.type.StatType;

/**
 * StatFacade is a class that provides an interface for tracking and recording various types of statistics.
 * It provides access to basic and indexed statistical data storage.
 *
 * This class is a part of the "stats" package and implements the StatRecorderFacade interface.
 */
public class StatFacade implements StatRecorderFacade {

    /**
     * The data store for basic statistics.
     */
    private final StatRecorder<BasicStatAggregate> basicDataStore;

    /**
     * The data store for indexed statistics.
     */
    private final StatRecorder<IndexedStatAggregate> indexedDataStore;

    /**
     * The constructor for the StatFacade class.
     *
     * @param basicDataStore the data store for basic statistics.
     * @param indexedDataStore the data store for indexed statistics.
     */
    public StatFacade(StatDataStore<BasicStatAggregate> basicDataStore, StatDataStore<IndexedStatAggregate> indexedDataStore) {
        this.basicDataStore = new StatRecorder<>(basicDataStore);
        this.indexedDataStore = new StatRecorder<>(indexedDataStore);
    }

    /**
     * Getter method for the basic statistic recorder.
     *
     * @return the basic statistic recorder.
     */
    public StatRecorder<BasicStatAggregate> getBasicRecorder() {
        return basicDataStore;
    }

    /**
     * Getter method for the indexed statistic recorder.
     *
     * @return the indexed statistic recorder.
     */
    public StatRecorder<IndexedStatAggregate> getIndexedRecorder() {
        return indexedDataStore;
    }

    /**
     * The record method takes a StatType object and an entry of the generic type T.
     * It processes the entry using the StatType object.
     *
     * @param type a StatType object.
     * @param entry the data entry to be processed.
     * @param <T> the type of data to be processed.
     */
    @Override
    public <T> void record(StatType<T, ?> type, T entry) {
        type.process(this, entry);
    }

}
