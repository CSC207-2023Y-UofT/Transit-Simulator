package stats.aggregator.average;

public class AverageAggregate implements StatAggregate {

    private final int count;
    private final double average;

    public AverageAggregate(int count, double average) {
        this.count = count;
        this.average = average;
    }

    public int getCount() {
        return count;
    }

    @Override
    public double value() {
        return average;
    }
}
