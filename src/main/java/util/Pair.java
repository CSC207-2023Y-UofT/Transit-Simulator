package util;

/**
 * A generic class that represents a pair of objects of types T and K.
 *
 * @param <T> the type of the first element in the pair.
 * @param <K> the type of the second element in the pair.
 */
public class Pair<T, K> {
    private final T first;
    private final K second;

    /**
     * Constructs a new pair with the given elements.
     *
     * @param first the first element of the pair.
     * @param second the second element of the pair.
     */
    public Pair(T first, K second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns the first element of the pair.
     *
     * @return the first element of the pair.
     */
    public T getFirst() {
        return first;
    }

    /**
     * Returns the second element of the pair.
     *
     * @return the second element of the pair.
     */
    public K getSecond() {
        return second;
    }

    /**
     * Determines whether the specified object is equal to the current object.
     * Two Pairs are equal if their first and second elements are both equal.
     *
     * @param other the object to compare with the current object.
     * @return true if the specified object is equal to the current object; otherwise, false.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Pair)) {
            return false;
        }

        Pair<?, ?> otherPair = (Pair<?, ?>) other;
        return first.equals(otherPair.first) && second.equals(otherPair.second);
    }

    /**
     * Returns a hash code for this pair. The hash code is computed based on the
     * hash codes of the first and second elements of the pair.
     *
     * @return a hash code for this pair.
     */
    @Override
    public int hashCode() {
        return first.hashCode() ^ second.hashCode();
    }

    /**
     * Returns a string representation of this pair. The returned string is of the form
     * "(first, second)".
     *
     * @return a string representation of this pair.
     */
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
