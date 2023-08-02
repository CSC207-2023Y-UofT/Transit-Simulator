package stats.aggregate;

import java.io.Serializable;

/**
 * This abstract class represents a SingletonAggregate, which holds a single value of any type that implements Serializable interface.
 * This can be used for aggregating a single value from a data stream or any other source.
 */
public abstract class SingletonAggregate<T> implements Serializable {

    /**
     * Holds the aggregated value.
     */
    private final T total;

    /**
     * Constructor for SingletonAggregate.
     *
     * @param total The value to be aggregated.
     */
    public SingletonAggregate(T total) {
        this.total = total;
    }

    /**
     * Returns the aggregated value.
     *
     * @return The aggregated value of type T.
     */
    public T getValue() {
        return total;
    }

}
