package stats.aggregate;

/**
 * The ExpenseAggregate class extends the SingletonAggregate to manage the aggregation of expenses.
 * This class specifically deals with Double type values representing the total expense.
 */
public class ExpenseAggregate extends SingletonAggregate<Double> {

    /**
     * Constructor for the ExpenseAggregate class.
     *
     * @param total The total expense to be aggregated. This is a double value.
     */
    public ExpenseAggregate(double total) {
        super(total);
    }

}
