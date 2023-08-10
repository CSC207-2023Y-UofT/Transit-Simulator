package stats.aggregator;

import entity.ticket.Ticket;
import entity.ticket.TicketType;
import org.junit.jupiter.api.Test;
import stats.aggregate.RevenueAggregate;
import stats.aggregate.SingletonAggregate;
import stats.aggregate.ExpenseAggregate;
import stats.aggregator.impl.ExpenseAggregator;
import stats.aggregator.impl.RevenueAggregator;
import stats.entry.impl.expense.ExpenseStat;
import stats.entry.impl.expense.MaintenanceStat;
import stats.entry.impl.revenue.RevenueStat;
import stats.entry.impl.revenue.TicketSaleStat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RevenueAggregatorTest {

    @Test
    void aggregate() {
        RevenueAggregator revAggr = new RevenueAggregator();

        List<RevenueStat> revenueStats = List.of(
                new TicketSaleStat(new Ticket(TicketType.CHILD)),
                new TicketSaleStat(new Ticket(TicketType.ADULT))
        );

        RevenueAggregate aggr = revAggr.aggregate(revenueStats);
        assert aggr.getValue() == TicketType.CHILD.getPrice() + TicketType.ADULT.getPrice();
    }

    @Test
    void aggregateExisting() {

        RevenueAggregator revAggr = new RevenueAggregator();

        List<RevenueAggregate> aggregates = List.of(
                new RevenueAggregate(1.0),
                new RevenueAggregate(1000.0)
        );

        RevenueAggregate aggr = revAggr.aggregateExisting(aggregates);

        assertEquals(1001.0, aggr.getValue());
    }

}