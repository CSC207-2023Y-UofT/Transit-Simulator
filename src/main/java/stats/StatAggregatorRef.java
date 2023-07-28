package stats;

import model.util.Preconditions;

import java.util.List;

/**
 * Interface for classes that aggregate statistics.
 */
public abstract class StatAggregatorRef<S extends StatEntry, A extends StatAggregate> {
    public abstract A aggregateStats(List<S> entries);

    public abstract A aggregateExisting(List<A> aggregates);

    protected void checkNotEmpty(List<?> list) {
        Preconditions.checkArgument(!list.isEmpty(), "Cannot aggregate empty list");
    }
}