package stats.entry.impl;

/**
 * This class represents a specific type of {@link ExpenseStat} related to electricity usage expenses.
 */
public class ElectricityUsageStat implements ExpenseStat {

    /**
     * The amount of electricity used represented as a double value.
     */
    private final double amount;

    /**
     * Constructs an ElectricityUsageStat instance with a specified amount of electricity used.
     *
     * @param amount The amount of electricity used.
     */
    public ElectricityUsageStat(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the amount of electricity used.
     *
     * @return The amount of electricity used as a double value.
     */
    public double getAmount() {
        return amount;
    }

    @Override
    public double getExpense() {
        return amount;
    }
}
