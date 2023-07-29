package stats.type;

import stats.StatReport;
import stats.aggregate.IndexedStatAggregate;
import stats.aggregate.BasicStatAggregate;
import stats.StatFacade;
import util.Pair;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class IndexedStatType<T> extends StatType<T, IndexedStatAggregate> {

    private final Function<T, Pair<String, Double>> transform;

    public IndexedStatType(
            String identifier,
            String name,
            Function<T, Pair<String, Double>> transform
    ) {
        super(identifier, name);
        this.transform = transform;
    }

    @Override
    public void process(StatFacade tracker, T entry) {
        Pair<String, Double> pair = transform.apply(entry);
        tracker.getIndexedRecorder().record(
                this,
                new IndexedStatAggregate(pair.getFirst(), new BasicStatAggregate(pair.getSecond()))
        );
    }

    @Override
    public CompletableFuture<StatReport<IndexedStatAggregate>> getReport(StatFacade tracker, long startMinute, long endMinute) {
        return tracker.getIndexedRecorder().getReport(
                this,
                startMinute,
                endMinute
        );
    }

}
