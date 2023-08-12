package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {
    @Test
    public void testPair() {
        Pair<String, Integer> pair = new Pair<>("Hello", 1);
        assertEquals("Hello", pair.getFirst());
        assertEquals(1, pair.getSecond());
    }

}