package train.util;

public class Preconditions {
    public static void checkArgument(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkState(boolean state, String message) {
        if (!state) {
            throw new IllegalStateException(message);
        }
    }
}
