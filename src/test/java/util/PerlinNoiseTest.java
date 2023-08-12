package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerlinNoiseTest {

    @Test
    public void testNoise() {
        PerlinNoise perlinNoise = new PerlinNoise(123, 256);
        double noise = perlinNoise.noise(0.5);
        assert noise >= -1 && noise <= 1;
    }

}