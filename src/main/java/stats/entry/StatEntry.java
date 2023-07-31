package stats.entry;

import java.io.Serializable;

/**
 * Stat entries are the data that is aggregated. They are stored in the entry data store.
 * They are also used to generate the aggregates, which are also stored, but in
 * the aggregate data store. Entry classes should generally not change, but could be changed
 * as long as the serialization methods are updated accordingly.
 */
public interface StatEntry extends Serializable {

    /**
     * This is the hierarchy of stat entries. It is used to determine which
     * stat entries are also forms of other stat entries. For example, a
     * {@link stats.entry.impl.MaintenanceStat} is also a {@link stats.entry.impl.ExpenseStat}.
     */
    EntryHierarchy HIERARCHY = new EntryHierarchy();

}
