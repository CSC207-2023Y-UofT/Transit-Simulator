package stats.aggregate;

/**
 * Defines a type of stat aggregate that can be merged
 * with another aggregate of the same type.
 * @param <A> The type of the aggregate. This should be the
 *           same type as the implementing class.
 */
public interface StatAggregate<A> {

    /**
     * Merges this aggregate with another aggregate of the same type.
     * @param other The other aggregate to merge with.
     * @return A new aggregate that is the result of merging this aggregate
     * with the other aggregate.
     */
    A merge(A other);

}
