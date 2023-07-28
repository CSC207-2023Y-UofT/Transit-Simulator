package stats.aggregator.min;

public class MinAggregate implements StatAggregate {

    private final double max;

    public MinAggregate(double max) {
        this.max = max;
    }

    @Override
    public double value() {
        return max;
    }
}
