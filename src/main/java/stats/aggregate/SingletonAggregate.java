package stats.aggregate;

import java.io.Serializable;

public abstract class SingletonAggregate<T> implements Serializable {
    private final T total;

    public SingletonAggregate(T total) {
        this.total = total;
    }

    public T getValue() {
        return total;
    }

}
