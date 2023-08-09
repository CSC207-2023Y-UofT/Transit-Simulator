package util;

/**
 * A utility class that provides methods for precondition checking.
 * <p>
 * These methods are useful for preventing errors and bugs due to invalid
 * arguments or illegal states.
 */
public class Preconditions {

    /**
     * Checks if a condition is true. If the condition is false,
     * this method throws an IllegalArgumentException with the specified message.
     *
     * @param condition the condition to check.
     * @param message   the message to use for the exception if the condition is false.
     * @throws IllegalArgumentException if the condition is false.
     */
    public static void checkArgument(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if a state is true. If the state is false,
     * this method throws an IllegalStateException with the specified message.
     *
     * @param state   the state to check.
     * @param message the message to use for the exception if the state is false.
     * @throws IllegalStateException if the state is false.
     */
    public static void checkState(boolean state, String message) {
        if (!state) {
            throw new IllegalStateException(message);
        }
    }

}
