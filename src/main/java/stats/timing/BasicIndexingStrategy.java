package stats.timing;

import util.Preconditions;

public class BasicIndexingStrategy implements IndexingStrategy {

    /**
     * The length of each time index in ms.
     */
    private final long indexLength;

    /**
     * Create a new BasicTimeIndexProvider.
     *
     * @param indexLength The size of each time index in ms.
     * @throws IllegalArgumentException if indexSize is not positive.
     */
    public BasicIndexingStrategy(long indexLength) {
        Preconditions.checkArgument(indexLength > 0, "indexSize must be positive");
        this.indexLength = indexLength;
    }

    // Inherited javadocs
    @Override
    public long getTimeIndex() {
        return getTimeIndex(System.currentTimeMillis());
    }

    // Inherited javadocs
    @Override
    public long getTimeIndex(long epochTime) {
        return epochTime / indexLength;
    }
}
