package stats;

/**
 * Interface for events that have an expense.
 */
public interface ExpenseStat extends StatEntry {

    /**
     * Return the expense.
     */
    double getExpense();

}
