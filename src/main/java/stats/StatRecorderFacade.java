package stats;

import stats.type.StatType;

/**
 * An interface for classes responsible for recording statistics.
 */
public interface StatRecorderFacade {

    /**
     * Records an entry based on its type.
     *
     * @param type  the type of data being recorded, represented by a StatType object.
     * @param entry the data entry to be recorded, of type T.
     * @param <T>   the generic type of the data entry to be recorded.
     */
    <T> void record(StatType<T, ?> type, T entry);

}
