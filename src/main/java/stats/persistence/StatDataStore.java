package stats.persistence;

import stats.StatEntry;

public interface StatDataStore {
    void recordNew(StatEntry entry);

}
