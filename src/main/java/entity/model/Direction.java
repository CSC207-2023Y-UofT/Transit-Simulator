package entity.model;

/**
 * The Direction enum represents the direction of movement along a track segment.
 * It defines two directions: FORWARD and BACKWARD.
 * Each direction is associated with a multiplier that affects how distance and movement are calculated.
 */
public enum Direction {
    /**
     * The FORWARD direction represents movement in the positive direction along a track segment.
     * When moving forward, distances and movements are positive.
     */
    FORWARD(1),

    /**
     * The BACKWARD direction represents movement in the negative direction along a track segment.
     * When moving backward, distances and movements are negative.
     */
    BACKWARD(-1);

    /**
     * The multiplier associated with the direction.
     */
    private final int multiplier;

    /**
     * Creates a Direction enum with the specified multiplier.
     *
     * @param multiplier The multiplier associated with the direction.
     */
    Direction(int multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Gets the multiplier associated with the direction.
     * The multiplier affects how distance and movement are calculated in the specified direction.
     *
     * @return The multiplier value.
     */
    public int getMultiplier() {
        return multiplier;
    }

    /**
     * Gets the opposite direction of the current direction.
     * If the current direction is FORWARD, it returns BACKWARD, and vice versa.
     *
     * @return The opposite direction.
     */
    public Direction opposite() {
        return this == FORWARD ? BACKWARD : FORWARD;
    }
}
