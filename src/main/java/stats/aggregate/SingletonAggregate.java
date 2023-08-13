package stats.aggregate;

import java.io.Serializable;
import java.util.Objects;

// Entity
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingletonAggregate<?> that = (SingletonAggregate<?>) o;
        return Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(total);
    }
}
