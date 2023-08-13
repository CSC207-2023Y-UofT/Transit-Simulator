package interface_adapter.controller;

import stats.StatTracker;
import stats.aggregator.StatAggregator;
import stats.entry.StatEntry;
import stats.timing.BasicTimeIndexingStrategy;
import stats.timing.TimeIndexingStrategy;

import java.io.Serializable;
import java.util.*;

public class DummyStatTracker implements StatTracker {
    @Override
    public void record(StatEntry entry) {}

    @Override
    public TimeIndexingStrategy getIndexingStrategy() {
        return new BasicTimeIndexingStrategy(60000);
    }

    @Override
    public void flush() {}

    @Override
    public void flush(long index) {}

    @Override
    public boolean shouldFlush() {
        return false;
    }

    @Override
    public <E extends StatEntry> List<E> getEntries(Class<E> entryClass, long index) {
        return new ArrayList<>();
    }

    @Override
    public <E extends StatEntry, A> Map<Long, A> getAggregates(Class<E> entryClass, Class<A> aggregateClass, long fromIndex, long toIndexInclusive) {
        return new HashMap<>();
    }

    @Override
    public <E extends StatEntry, A extends Serializable> Map<Long, A> getOrAggregate(StatAggregator<E, A> aggregator, long startIndex, long endIndexInclusive) {
        return new HashMap<>();
    }

    @Override
    public <E extends StatEntry, A extends Serializable> Optional<A> aggregateCurrent(StatAggregator<E, A> aggregator) {
        return Optional.empty();
    }
}
