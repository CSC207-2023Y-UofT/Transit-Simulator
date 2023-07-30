package stats.persistence;

import stats.StatReport;
import stats.type.StatType;

import java.util.concurrent.CompletableFuture;

public interface StatDataStore<A> {
    void record(StatType<?, A> type, long minute, A aggregate);
    CompletableFuture<StatReport<A>> getReport(
            StatType<?, A> type,
            long startMinute,
            long endMinute
    );
}
