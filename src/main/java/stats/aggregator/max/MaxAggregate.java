package stats.aggregator.max;

public class MaxAggregate implements StatAggregate {

    private final double max;

    public MaxAggregate(double max) {
        this.max = max;
    }

    @Override
    public double value() {
        return max;
    }

}
