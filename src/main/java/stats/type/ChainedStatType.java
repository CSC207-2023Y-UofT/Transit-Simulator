package stats.type;

import stats.StatReport;
import stats.StatFacade;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * A class representing a chained statistical type that processes data of type T and produces aggregated data of type A.
 * This class extends {@link StatType} and provides implementation for processing data and obtaining reports.
 */
public class ChainedStatType<T, A, OT, OA> extends StatType<T, A> {
    private final StatType<T, A> wrapped;
    private final StatType<OT, OA> next;
    private final Function<T, OT> transform;

    /**
     * Constructs a ChainedStatType that chains two StatTypes together to process data.
     *
     * @param wrapped   The first StatType to process data of type T and produce aggregated data of type A.
     * @param next      The next StatType to process data of type OT (output of the first StatType)
     *                  and produce aggregated data of type OA.
     * @param transform The function to transform data of type T to data of type OT for the next StatType.
     */
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

    /**
     * Processes the provided entry using the first StatType and then using the next StatType with transformed data.
     *
     * @param tracker The StatFacade used to record the processed data.
     * @param entry   The data entry to be processed.
     */
    public void process(StatFacade tracker, T entry) {
        wrapped.process(tracker, entry);
        next.process(tracker, transform.apply(entry));
    }

    /**
     * Retrieves the statistical report from the first StatType for the specified time range.
     *
     * @param tracker     The StatFacade used to fetch the statistical report.
     * @param startMinute The start time in minutes (Unix timestamp) of the desired time range (inclusive).
     * @param endMinute   The end time in minutes (Unix timestamp) of the desired time range (exclusive).
     * @return A CompletableFuture representing the statistical report for the given time range.
     */
    @Override
    public CompletableFuture<StatReport<A>> getReport(StatFacade tracker, long startMinute, long endMinute) {
        return wrapped.getReport(tracker, startMinute, endMinute);
    }

}
