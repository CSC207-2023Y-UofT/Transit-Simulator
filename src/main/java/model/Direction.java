package model;

public enum Direction {
    FORWARD(-1),
    BACKWARD(1);

    private final int multiplier;
    Direction(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getMultiplier() {
        return multiplier;
    }
}
