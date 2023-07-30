package stats;

import stats.aggregator.ExpenseAggregator;
import stats.aggregator.ExpenseAggregate;
import stats.persistence.StatAggregateDataStore;
import stats.persistence.StatDataController;
import stats.persistence.StatEntryDataStore;
import stats.persistence.impl.FileAggregateDataStore;
import stats.persistence.impl.FileEntryDataStore;

import java.io.File;

public class ExampleController {

    private final StatDataController dataController;

    public ExampleController() {
        StatEntryDataStore entryDataStore = new FileEntryDataStore(new File("stats/entries"));
        StatAggregateDataStore aggregateDataStore = new FileAggregateDataStore(new File("stats/aggregates"));
        this.dataController = new StatDataController(entryDataStore, aggregateDataStore);
    }

    public double getExpenses(long beginTimeIndex, long endTimeIndex) {
        ExpenseAggregator aggregator = new ExpenseAggregator();
        ExpenseAggregate aggregate = aggregator.aggregate(dataController, beginTimeIndex, endTimeIndex);
        return aggregate.getExpensesTotal();
    }
}
