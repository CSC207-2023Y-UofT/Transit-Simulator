package stats.aggregate;

/**
 * A class representing basic statistical aggregates, including mean, minimum, maximum, and count.
 * This class implements the {@link StatAggregate} interface for merging statistical aggregates.
 */
public class BasicStatAggregate implements StatAggregate<BasicStatAggregate> {

    /**
     * The mean value of the data.
     */
    private final double mean;

    /**
     * The minimum value of the data.
     */
    private final double min;

    /**
     * The maximum value of the data.
     */
    private final double max;

    /**
     * The count of data points.
     */
    private final int count;

    /**
     * Constructs a BasicStatAggregate with the specified statistical values.
     *
     * @param mean  The mean value of the data.
     * @param min   The minimum value of the data.
     * @param max   The maximum value of the data.
     * @param count The count of data points.
     */
    public BasicStatAggregate(double mean, double min, double max, int count) {
        this.mean = mean;
        this.min = min;
        this.max = max;
        this.count = count;
    }

    /**
     * Constructs a BasicStatAggregate with a single value.
     * This constructor is useful when initializing the aggregate with the first data point.
     *
     * @param value The single value to set as the mean, minimum, and maximum.
     */
    public BasicStatAggregate(double value) {
        this(value, value, value, 1);
    }

    /**
     * Returns the mean value of the data.
     *
     * @return The mean value.
     */
    public double getMean() {
        return mean;
    }

    /**
     * Returns the count of data points.
     *
     * @return The count of data points.
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns the maximum value of the data.
     *
     * @return The maximum value.
     */
    public double getMax() {
        return max;
    }

    /**
     * Returns the minimum value of the data.
     *
     * @return The minimum value.
     */
    public double getMin() {
        return min;
    }

    /**
     * Merges this BasicStatAggregate with another BasicStatAggregate instance.
     * The resulting aggregate will include statistics from both instances.
     *
     * @param other The other BasicStatAggregate instance to merge with this one.
     * @return A new BasicStatAggregate containing the merged statistics.
     */
    public BasicStatAggregate merge(BasicStatAggregate other) {
        double max = Math.max(this.max, other.max);
        double min = Math.min(this.min, other.min);
        int count = this.count + other.count;
        double mod1 = (double) this.count / count;
        double mod2 = (double) other.count / count;
        double mean = this.mean * mod1 + other.mean * mod2;
        return new BasicStatAggregate(mean, min, max, count);
    }

}
