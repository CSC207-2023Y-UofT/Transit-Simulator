package stats.aggregate;

import java.io.Serializable;

public class SingletonAggregate implements Serializable {
    private final double total;

    public SingletonAggregate(double total) {
        this.total = total;
    }

    public double getTotal() {
        return total;
    }
}
