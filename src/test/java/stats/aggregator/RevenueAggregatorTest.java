package stats.aggregator;

import org.junit.jupiter.api.Test;
import stats.aggregate.DoubleAggregate;
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

        DoubleAggregate doubleAggregate = expenseAggregator.aggregate(expenseStats);
        assertEquals(1001.0, doubleAggregate.getTotal());
    }

    @Test
    void aggregateExisting() {

        ExpenseAggregator expenseAggregator = new ExpenseAggregator();

        List<DoubleAggregate> expenseAggregates = List.of(
                new DoubleAggregate(1.0),
                new DoubleAggregate(1000.0)
        );

        DoubleAggregate doubleAggregate = expenseAggregator.aggregateExisting(expenseAggregates);

        assertEquals(1001.0, doubleAggregate.getTotal());
    }

}