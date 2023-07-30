package stats.persistence;

import stats.StatReport;
import stats.aggregate.BasicStatAggregate;
import stats.type.StatType;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class BasicStatDataStore implements StatDataStore<BasicStatAggregate> {
    private final File folder;

    public BasicStatDataStore(File folder) {
        this.folder = folder;
    }

    @Override
    public void record(StatType<?, BasicStatAggregate> type, long minute, BasicStatAggregate aggregate) {
        String id = type.getIdentifier();
        File
    }

    @Override
    public CompletableFuture<StatReport<BasicStatAggregate>> getReport(StatType<?, BasicStatAggregate> type, long startMinute, long endMinute) {
        return null;
    }
}
