package stats;

import java.util.Map;
import java.util.Optional;

/**
 * A class to hold a statistical report.
 * The report is represented as a map where the keys are timestamps (in minutes) and the values are StatAggregate instances.
 */
public class StatReport<A> {

    /**
     * A map holding the statistical data.
     */
    private final Map<Long, A> data;

    /**
     * Constructor for the StatReport class.
     *
     * @param data a map holding the statistical data.
     */
    public StatReport(Map<Long, A> data) {
        this.data = data;
    }

    /**
     * Returns the statistical data.
     *
     * @return the statistical data.
     */
    public Map<Long, A> getData() {
        return data;
    }

    /**
     * Method to get the StatAggregate associated with a particular minute.
     *
     * @param minute the minute for which the StatAggregate is required.
     * @return an Optional containing the StatAggregate for the specified minute if it exists.
     */
    public Optional<A> getAggregate(long minute) {
        return Optional.ofNullable(data.get(minute));
    }

}
