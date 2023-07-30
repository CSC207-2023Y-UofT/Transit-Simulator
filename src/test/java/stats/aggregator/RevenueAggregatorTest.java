package stats.aggregator;

import org.junit.jupiter.api.Test;
import stats.aggregate.SingletonAggregate;
import stats.aggregator.expense.ExpenseAggregate;
import stats.aggregator.expense.ExpenseAggregator;
import stats.entry.impl.ExpenseStat;
import stats.entry.impl.MaintenanceStat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RevenueAggregatorTest {

    @Test
    void aggregate() {
        ExpenseAggregator expenseAggregator = new ExpenseAggregator();

        List<ExpenseStat> expenseStats = List.of(
                new MaintenanceStat(1.0),
                new MaintenanceStat(1000.0)
        );

        SingletonAggregate singletonAggregate = expenseAggregator.aggregate(expenseStats);
        assertEquals(1001.0, singletonAggregate.getTotal());
    }

    @Test
    void aggregateExisting() {

        ExpenseAggregator expenseAggregator = new ExpenseAggregator();

        List<ExpenseAggregate> expenseAggregates = List.of(
                new ExpenseAggregate(1.0),
                new ExpenseAggregate(1000.0)
        );

        ExpenseAggregate singletonAggregate = expenseAggregator.aggregateExisting(expenseAggregates);

        assertEquals(1001.0, singletonAggregate.getTotal());
    }

}