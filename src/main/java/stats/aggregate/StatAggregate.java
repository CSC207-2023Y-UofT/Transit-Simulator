package stats.aggregate;

public interface StatAggregate<A> {
    A merge(A other);
}
