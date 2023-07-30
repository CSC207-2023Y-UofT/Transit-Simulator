package stats.persistence;

import stats.entry.StatEntry;

import java.util.Optional;

public interface StatAggregateDataStore {
    <E extends StatEntry, A> void store(long index,
                                        Class<? extends E> entryClass,
                                        Class<? extends A> aggregateClass,
                                        A aggregate);

    <E extends StatEntry, A> Optional<A> retrieve(Class<E> entryClass,
                                                  Class<A> aggregateClass,
                                                  long index);
}
