package stats.aggregate;

/**
 * Basic representation of an aggregated set of a numeric statistic.
 */
public class BasicStatAggregate implements StatAggregate<BasicStatAggregate> {
    private final double mean;
    private final double min;
    private final double max;
    private final int count;

    public BasicStatAggregate(double mean, double min, double max, int count) {
        this.mean = mean;
        this.min = min;
        this.max = max;
        this.count = count;
    }

    public BasicStatAggregate(double value) {
        this(value, value, value, 1);
    }

    public double getMean() {
        return mean;
    }

    public int getCount() {
        return count;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

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
