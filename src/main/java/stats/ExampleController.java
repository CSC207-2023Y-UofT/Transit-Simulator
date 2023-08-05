package stats;

import stats.aggregator.expense.ExpenseAggregate;
import stats.aggregator.expense.ExpenseAggregator;
import stats.persistence.StatAggregateDataStore;
import stats.persistence.StatDataController;
import stats.persistence.StatEntryDataStore;
import stats.persistence.impl.FileAggregateDataStore;
import stats.persistence.impl.FileEntryDataStore;

import java.io.File;

/**
 * Example controller for stats.
 */
public class ExampleController {

    /**
     * The data controller.
     */
    private final StatDataController dataController;

    /**
     * Constructs an instance of ExampleController.
     */
    public ExampleController() {
        StatEntryDataStore entryDataStore = new FileEntryDataStore(new File("stats/entries"));
        StatAggregateDataStore aggregateDataStore = new FileAggregateDataStore(new File("stats/aggregates"));
        this.dataController = new StatDataController(entryDataStore, aggregateDataStore);
    }

    /**
     * Gets the expenses for a given time range.
     *
     * @param beginTimeIndex the beginning time index
     * @param endTimeIndex   the ending time index
     * @return the expenses
     */
    public double getExpenses(long beginTimeIndex, long endTimeIndex) {
        ExpenseAggregator aggregator = new ExpenseAggregator();
        ExpenseAggregate aggregate = aggregator.aggregate(dataController, beginTimeIndex, endTimeIndex).orElse(null);
        return aggregate == null ? 0 : aggregate.getValue();
    }
}
