package stats.aggregate;

import java.util.HashMap;
import java.util.Map;

public class IndexedStatAggregate implements StatAggregate<IndexedStatAggregate> {
    private final Map<String, BasicStatAggregate> aggregates;

    public IndexedStatAggregate(Map<String, BasicStatAggregate> aggregates) {
        this.aggregates = aggregates;
    }

    public IndexedStatAggregate(String key, BasicStatAggregate aggregate) {
        Map<String, BasicStatAggregate> map = new HashMap<>();
        map.put(key, aggregate);
        this.aggregates = map;
    }

    public Map<String, BasicStatAggregate> getAggregates() {
        return aggregates;
    }

    public BasicStatAggregate getAggregate(String key) {
        return aggregates.get(key);
    }

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
