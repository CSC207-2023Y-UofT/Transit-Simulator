package stats.entry;

import java.io.Serializable;

/**
 * Stat entries are the data that is aggregated. They are stored in the entry data store.
 * They are also used to generate the aggregates, which are also stored, but in
 * the aggregate data store. Entry classes should generally not change, but could be changed
 * as long as the serialization methods are updated accordingly.
 */
public interface StatEntry extends Serializable {
    EntryHierarchy HIERARCHY = new EntryHierarchy();
}
