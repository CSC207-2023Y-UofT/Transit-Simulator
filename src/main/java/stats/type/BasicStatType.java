package stats.type;

import stats.StatReport;
import stats.aggregate.BasicStatAggregate;
import stats.StatFacade;
import util.Preconditions;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class BasicStatType<T> extends StatType<T, BasicStatAggregate> {

    private final Function<T, Double> transform;

    public BasicStatType(
            String identifier,
            String name,
            Function<T, Double> transform
    ) {
        super(identifier, name);
        this.transform = transform;
    }

    @Override
    public void process(StatFacade tracker, T entry) {
        Double value = transform.apply(entry);
        Preconditions.checkArgument(value != null, "transform function returned null");
        tracker.getBasicRecorder().record(this, new BasicStatAggregate(value));
    }

    @Override
    public CompletableFuture<StatReport<BasicStatAggregate>> getReport(StatFacade tracker, long startMinute, long endMinute) {
        return tracker.getBasicRecorder().getReport(
                this,
                startMinute,
                endMinute
        );
    }
}
