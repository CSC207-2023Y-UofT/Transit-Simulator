package stats.aggregate;

/**
 * Defines a type of stat aggregate that can be merged
 * with another aggregate of the same type.
 * @param <A> The type of the aggregate. This should be the
 *           same type as the implementing class.
 */
public interface StatAggregate<A> {
    A merge(A other);
}
