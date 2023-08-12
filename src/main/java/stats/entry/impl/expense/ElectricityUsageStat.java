package stats.entry.impl.expense;

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

    @Override
    public double getExpense() {
        return amount;
    }
}
