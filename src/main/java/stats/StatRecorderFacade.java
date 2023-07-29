package stats;

import stats.type.StatType;

public interface StatRecorderFacade {
    <T> void record(StatType<T, ?> type, T entry);
}
