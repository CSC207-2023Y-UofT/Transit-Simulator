package stats.aggregate;

import java.io.Serializable;

public class DoubleAggregate implements Serializable {
    private final double total;

    public DoubleAggregate(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }
}
