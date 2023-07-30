package stats.type;

import stats.StatReport;
import stats.aggregate.IndexedStatAggregate;
import stats.aggregate.BasicStatAggregate;
import stats.StatFacade;
import util.Pair;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * A class representing an indexed statistical type that processes data of type T and produces IndexedStatAggregate.
 * This class extends {@link StatType} and provides implementation for processing data and obtaining reports.
 */
public class IndexedStatType<T> extends StatType<T, IndexedStatAggregate> {

    /**
     * The function to transform data of type T to a Pair of String and Double values representing the index and data value, respectively.
     */
    private final Function<T, Pair<String, Double>> transform;

    /**
     * Constructs an IndexedStatType with the given identifier, name, and transformation function.
     *
     * @param identifier The unique identifier for this statistical type.
     * @param name       The human-readable name of this statistical type.
     * @param transform  The function to transform data of type T to a Pair of String and Double values
     *                   representing the index and data value, respectively.
     */
    public IndexedStatType(
            String identifier,
            String name,
            Function<T, Pair<String, Double>> transform
    ) {
        super(identifier, name);
        this.transform = transform;
    }

    /**
     * Processes the provided entry using the transform function and records the result in the tracker.
     *
     * @param tracker The StatFacade used to record the processed data.
     * @param entry   The data entry to be processed.
     */
    @Override
    public void process(StatFacade tracker, T entry) {
        Pair<String, Double> pair = transform.apply(entry);
        tracker.getIndexedRecorder().record(
                this,
                new IndexedStatAggregate(pair.getFirst(), new BasicStatAggregate(pair.getSecond()))
        );
    }

    /**
     * Retrieves the statistical report for this type from the tracker for the specified time range.
     *
     * @param tracker     The StatFacade used to fetch the statistical report.
     * @param startMinute The start time in minutes (Unix timestamp) of the desired time range (inclusive).
     * @param endMinute   The end time in minutes (Unix timestamp) of the desired time range (exclusive).
     * @return A CompletableFuture representing the statistical report for the given time range.
     */
    @Override
    public CompletableFuture<StatReport<IndexedStatAggregate>> getReport(StatFacade tracker, long startMinute, long endMinute) {
        return tracker.getIndexedRecorder().getReport(
                this,
                startMinute,
                endMinute
        );
    }

}
