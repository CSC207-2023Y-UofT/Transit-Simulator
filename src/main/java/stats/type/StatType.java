package stats.type;

import stats.StatReport;
import stats.StatFacade;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * A class representing a statistical type that processes data of type T and produces aggregated data of type A.
 * This class provides the interface for processing data and obtaining reports.
 */
public abstract class StatType<T, A> {

    /**
     * The unique identifier for this statistical type.
     */
    private final String identifier;

    /**
     * The human-readable name of this statistical type.
     */
    private final String name;

    /**
     * Constructs a StatType with the given identifier and name.
     *
     * @param identifier The unique identifier for this statistical type.
     * @param name       The human-readable name of this statistical type.
     */
    protected StatType(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
    }

    /**
     * Returns the unique identifier for this statistical type.
     *
     * @return The identifier of this statistical type.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns the human-readable name of this statistical type.
     *
     * @return The name of this statistical type.
     */
    public String getName() {
        return name;
    }

    /**
     * Processes the provided entry using the data from the StatFacade tracker.
     * This method is responsible for updating the tracker with the processed data.
     *
     * @param tracker The StatFacade used to record the processed data.
     * @param entry   The data entry to be processed.
     */
    public abstract void process(StatFacade tracker, T entry);

    /**
     * Retrieves the statistical report for this type from the tracker for the specified time range.
     *
     * @param tracker     The StatFacade used to fetch the statistical report.
     * @param startMinute The start time in minutes (Unix timestamp) of the desired time range (inclusive).
     * @param endMinute   The end time in minutes (Unix timestamp) of the desired time range (exclusive).
     * @return A CompletableFuture representing the statistical report for the given time range.
     */
    public abstract CompletableFuture<StatReport<A>> getReport(
            StatFacade tracker,
            long startMinute,
            long endMinute
    );

    /**
     * Chains this StatType with another StatType, producing a new chained StatType.
     * The chained StatType will process data using both StatTypes in sequence.
     *
     * @param next      The next StatType to process data of type OT (output of this StatType)
     *                  and produce aggregated data of type OA.
     * @param transform The function to transform data of type T to data of type OT for the next StatType.
     * @param <OT>      The type of data processed by the next StatType.
     * @param <OA>      The type of aggregated data produced by the next StatType.
     * @return The chained StatType that processes data using both StatTypes in sequence.
     */
    public <OT, OA> StatType<T, A> also(
            StatType<OT, OA> next,
            Function<T, OT> transform
    ) {
        return new ChainedStatType<>(this, next, transform);
    }

}
