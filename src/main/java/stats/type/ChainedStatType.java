package stats.type;

import stats.StatReport;
import stats.StatFacade;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ChainedStatType<T, A, OT, OA> extends StatType<T, A> {
    private final StatType<T, A> wrapped;
    private final StatType<OT, OA> next;
    private final Function<T, OT> transform;

    public ChainedStatType(
            StatType<T, A> wrapped,
            StatType<OT, OA> next,
            Function<T, OT> transform
    ) {
        super(wrapped.getIdentifier(), wrapped.getName());
        this.wrapped = wrapped;
        this.next = next;
        this.transform = transform;
    }

    public void process(StatFacade tracker, T entry) {
        wrapped.process(tracker, entry);
        next.process(tracker, transform.apply(entry));
    }

    @Override
    public CompletableFuture<StatReport<A>> getReport(StatFacade tracker, long startMinute, long endMinute) {
        return wrapped.getReport(tracker, startMinute, endMinute);
    }
}
