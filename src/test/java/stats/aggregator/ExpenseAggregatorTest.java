package stats.aggregator;

import org.junit.jupiter.api.Test;
import stats.aggregate.ExpenseAggregate;
import stats.aggregator.impl.ExpenseAggregator;
import stats.entry.impl.expense.ElectricityUsageStat;
import stats.entry.impl.expense.ExpenseStat;
import stats.entry.impl.expense.MaintenanceStat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpenseAggregatorTest {

    @Test
    void aggregate() {
        ExpenseAggregator expenseAggregator = new ExpenseAggregator();

        List<ExpenseStat> expenseStats = List.of(
                new MaintenanceStat(1.0),
                new MaintenanceStat(1000.0),
                new MaintenanceStat(),
                new ElectricityUsageStat(0.0)
        );

        ExpenseAggregate singletonAggregate = expenseAggregator.aggregate(expenseStats);
        assertEquals(1001.0, singletonAggregate.getValue());
    }

    @Test
    void aggregateExisting() {

        ExpenseAggregator expenseAggregator = new ExpenseAggregator();

        List<ExpenseAggregate> expenseAggregates = List.of(
                new ExpenseAggregate(1.0),
                new ExpenseAggregate(1000.0)
        );

        ExpenseAggregate singletonAggregate = expenseAggregator.aggregateExisting(expenseAggregates);

        assertEquals(1001.0, singletonAggregate.getValue());
    }

}