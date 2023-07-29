package stats.type;

import stats.StatReport;
import stats.StatFacade;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class StatType<T, A> {
    private final String identifier;
    private final String name;

    protected StatType(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public abstract void process(StatFacade tracker, T entry);
    public abstract CompletableFuture<StatReport<A>> getReport(
            StatFacade tracker,
            long startMinute,
            long endMinute
    );

    public <OT, OA> StatType<T, A> also(
            StatType<OT, OA> next,
            Function<T, OT> transform
    ) {
        return new ChainedStatType<>(this, next, transform);
    }
}
