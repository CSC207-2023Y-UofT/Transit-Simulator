package stats.entry.impl.expense;

import stats.entry.StatEntry;

/**
 * An interface representing an expense statistic entry. This entry extends the {@link StatEntry} interface.
 * Implementing classes should provide the logic for how to get the expense value.
 */
public interface ExpenseStat extends StatEntry {

    /**
     * Returns the expense value for this statistic entry.
     *
     * @return The expense value as a double.
     */
    double getExpense();
}
