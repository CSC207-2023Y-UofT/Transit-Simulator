package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class DirectionTest {
    @Test
    public void testGetMultiplier() {
        Assertions.assertEquals(-1, Direction.FORWARD.getMultiplier());
        Assertions.assertEquals(1, Direction.BACKWARD.getMultiplier());
    }

    @Test
    public void testOpposite() {
        Assertions.assertEquals(Direction.BACKWARD, Direction.FORWARD.opposite());
        Assertions.assertEquals(Direction.FORWARD, Direction.BACKWARD.opposite());
    }
}
