package stats.type;

import stats.StatReport;
import stats.aggregate.BasicStatAggregate;
import stats.StatFacade;
import util.Preconditions;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * A class representing a basic statistical type that processes data of type T and produces BasicStatAggregate.
 * This class extends {@link StatType} and provides implementation for processing data and obtaining reports.
 */
public class BasicStatType<T> extends StatType<T, BasicStatAggregate> {

    /**
     * The function to transform data of type T to a Double value for aggregation.
     */
    private final Function<T, Double> transform;

    /**
     * Constructs a BasicStatType with the given identifier, name, and transformation function.
     *
     * @param identifier The unique identifier for this statistical type.
     * @param name       The human-readable name of this statistical type.
     * @param transform  The function to transform data of type T to a Double value for aggregation.
     */
    public BasicStatType(
            String identifier,
            String name,
            Function<T, Double> transform
    ) {
        super(identifier, name);
        this.transform = transform;
    }

    /**
     * Processes the provided entry using the transform function and records the result in the tracker.
     *
     * @param tracker The StatFacade used to record the processed data.
     * @param entry   The data entry to be processed.
     * @throws IllegalArgumentException If the transform function returns null for the given entry.
     */
    @Override
    public void process(StatFacade tracker, T entry) {
        Double value = transform.apply(entry);
        Preconditions.checkArgument(value != null, "transform function returned null");
        tracker.getBasicRecorder().record(this, new BasicStatAggregate(value));
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
    public CompletableFuture<StatReport<BasicStatAggregate>> getReport(StatFacade tracker, long startMinute, long endMinute) {
        return tracker.getBasicRecorder().getReport(
                this,
                startMinute,
                endMinute
        );
    }

}
