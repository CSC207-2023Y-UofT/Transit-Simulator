package stats.type;

import stats.aggregate.BasicStatAggregate;
import stats.aggregate.IndexedStatAggregate;
import stats.event.CustomerEnterStationEvent;
import stats.event.MaintenanceEvent;
import stats.event.TicketSaleStat;
import util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stats {

    private static final List<StatType<?, ?>> TYPES = new ArrayList<>();

    public static List<StatType<?, ?>> values() {
        return Collections.unmodifiableList(TYPES);
    }

    public static final StatType<Double, BasicStatAggregate> REVENUE =
            register(new BasicStatType<>("revenue", "Revenue", revenue -> revenue));

    public static final StatType<Double, BasicStatAggregate> EXPENSES =
            register(new BasicStatType<>("expenses", "Expenses", expense -> expense));

    public static final StatType<TicketSaleStat, IndexedStatAggregate> TICKET_SALE =
            register(
                    new IndexedStatType<TicketSaleStat>("ticket_sale",
                            "Ticket Sale",
                            stat -> new Pair<>(stat.getTicket().getTypeId(), 1.0))
                            .also(REVENUE, TicketSaleStat::getRevenue)
            );

    public static final StatType<CustomerEnterStationEvent, IndexedStatAggregate> CUSTOMER_ENTER_STATION =
            register(
                    new IndexedStatType<>("customer_enter_station",
                            "Customer Enter Station",
                            e -> new Pair<>(e.getStation(), 1.0))
            );

    public static final StatType<CustomerEnterStationEvent, IndexedStatAggregate> CUSTOMER_EXIT_STATION =
            register(
                    new IndexedStatType<>("customer_exit_station",
                            "Customer Exit Station",
                            e -> new Pair<>(e.getStation(), 1.0))
            );

    public static final StatType<MaintenanceEvent, BasicStatAggregate> MAINTENANCE =
            register(
                    new BasicStatType<MaintenanceEvent>("maintenance",
                            "Maintenance",
                            e -> 1.0)
                            .also(EXPENSES, MaintenanceEvent::getExpense)
            );

    private static <T, A> StatType<T, A> register(StatType<T, A> type) {
        TYPES.add(type);
        return type;
    }

    private Stats() {}
}
