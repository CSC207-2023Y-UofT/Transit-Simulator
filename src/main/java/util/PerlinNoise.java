package util;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Class to generate Perlin Noise, used for the simulation
 * of passengers, so we get better-looking stats
 */
public class PerlinNoise {

    private final int[] permutationTable;
    private final double[] gradientValues;
    private final int size;

    /**
     * Initialize gradient values and a permutation table
     *
     * @param seed Randomness seed
     * @param size Size of the table
     */
    public PerlinNoise(int seed, int size) {
        this.gradientValues = new double[size];
        this.permutationTable = genInitialPermutations(size);
        this.size = size;

        Random rand = new Random(seed);

        // Shuffle the permutation table
        shuffleArray(this.permutationTable, rand);

        // Initialize gradient values, [-1, 1)
        for (int i = 0; i < size; i++) {
            this.gradientValues[i] = rand.nextDouble() * 2 - 1;
        }


    }

    /**
     * Helper method to generate an initial permutation table (0 to 255)
     */
    private int[] genInitialPermutations(int size) {
        return IntStream.range(0, size).toArray();
    }

    /**
     * Helper method to shuffle an array
     *
     * @param array  Array to shuffle
     * @param random Randomness source
     */
    private void shuffleArray(int[] array, Random random) {
        for (int i = 0; i < array.length; i++) {
            int j = random.nextInt(array.length - i) + i;
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    /**
     * Computes the 1-dimensional Perlin Noise value at given point
     *
     * @param position X-coordinate
     * @return Noise value at given point
     */
    public double noise(double position) {
        int positionInt = (int) position % permutationTable.length;
        double positionFractional = position - positionInt;

        double interpolationValue = fadeFunction(positionFractional);

        double gradientAtLeft = gradientValues[permutationTable[positionInt]];
        double gradientAtRight = gradientValues[permutationTable[(positionInt + 1) % size]];

        double leftContribution = gradientAtLeft * positionFractional;
        double rightContribution = gradientAtRight * (positionFractional - 1);

        return (1 - interpolationValue) * leftContribution + interpolationValue * rightContribution;
    }

    /**
     * Ken Perlin's fade function to smooth out transitions.
     *
     * @param value Value to fade.
     * @return Faded value.
     */
    private double fadeFunction(double value) {
        return value * value * value * (value * (value * 6 - 15) + 10);
    }

}