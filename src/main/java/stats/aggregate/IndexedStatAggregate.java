package stats.aggregate;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing indexed statistical aggregates, using a map to store aggregates associated with keys.
 * This class implements the {@link StatAggregate} interface for merging statistical aggregates.
 */
public class IndexedStatAggregate implements StatAggregate<IndexedStatAggregate> {
    private final Map<String, BasicStatAggregate> aggregates;

    /**
     * Constructs an IndexedStatAggregate with the given map of aggregates.
     *
     * @param aggregates The map of aggregates, where each entry is associated with a key.
     */
    public IndexedStatAggregate(Map<String, BasicStatAggregate> aggregates) {
        this.aggregates = aggregates;
    }

    /**
     * Constructs an IndexedStatAggregate with a single aggregate associated with a specific key.
     *
     * @param key       The key associated with the aggregate.
     * @param aggregate The BasicStatAggregate to be associated with the key.
     */
    public IndexedStatAggregate(String key, BasicStatAggregate aggregate) {
        Map<String, BasicStatAggregate> map = new HashMap<>();
        map.put(key, aggregate);
        this.aggregates = map;
    }

    /**
     * Returns the map of aggregates associated with keys.
     *
     * @return The map of aggregates.
     */
    public Map<String, BasicStatAggregate> getAggregates() {
        return aggregates;
    }


    /**
     * Retrieves the BasicStatAggregate associated with the given key.
     *
     * @param key The key associated with the aggregate.
     * @return The BasicStatAggregate associated with the specified key, or null if the key is not found.
     */
    public BasicStatAggregate getAggregate(String key) {
        return aggregates.get(key);
    }

    /**
     * Merges this IndexedStatAggregate with another IndexedStatAggregate instance.
     * The resulting aggregate will include statistics from both instances.
     *
     * @param other The other IndexedStatAggregate instance to merge with this one.
     * @return A new IndexedStatAggregate containing the merged statistics.
     */
    public IndexedStatAggregate merge(
            IndexedStatAggregate other
    ) {
        Map<String, BasicStatAggregate> otherAggregates = other.getAggregates();
        Map<String, BasicStatAggregate> mergedAggregates = new HashMap<>(aggregates);
        for (Map.Entry<String, BasicStatAggregate> entry : otherAggregates.entrySet()) {
            String key = entry.getKey();
            BasicStatAggregate existing = mergedAggregates.get(key);
            if (existing == null) {
                mergedAggregates.put(key, entry.getValue());
            } else {
                BasicStatAggregate merged = existing.merge(entry.getValue());
                mergedAggregates.put(key, merged);
            }
        }
        return new IndexedStatAggregate(mergedAggregates);
    }

}
