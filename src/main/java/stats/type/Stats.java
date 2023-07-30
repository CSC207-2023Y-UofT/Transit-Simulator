package stats.type;

import stats.aggregate.BasicStatAggregate;
import stats.aggregate.IndexedStatAggregate;
import stats.event.CustomerEnterStationEvent;
import stats.event.MaintenanceEvent;
import stats.event.SubwayEmergencyEvent;
import stats.event.TicketSaleStat;
import util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A utility class that defines and provides access to various statistical types for the subway system.
 * The class contains static fields for each statistical type, along with their corresponding identifiers,
 * names, and transformation functions.
 */
public class Stats {

    private static final List<StatType<?, ?>> TYPES = new ArrayList<>();

    /**
     * Returns an unmodifiable list of all registered statistical types.
     *
     * @return The list of registered statistical types.
     */
    public static List<StatType<?, ?>> values() {
        return Collections.unmodifiableList(TYPES);
    }

    /**
     * Represents the revenue statistical type for tracking revenue data.
     * The revenue value is the same as the input value (identity function).
     */
    public static final StatType<Double, BasicStatAggregate> REVENUE =
            register(new BasicStatType<>("revenue", "Revenue", revenue -> revenue));

    /**
     * Represents the expenses statistical type for tracking expenses data.
     * The expenses value is the same as the input value (identity function).
     */
    public static final StatType<Double, BasicStatAggregate> EXPENSES =
            register(new BasicStatType<>("expenses", "Expenses", expense -> expense));

    /**
     * Represents the ticket sale statistical type for tracking ticket sale data.
     * The ticket sale value is associated with the ticket type ID, and the count is always 1.
     */
    public static final StatType<TicketSaleStat, IndexedStatAggregate> TICKET_SALE =
            register(
                    new IndexedStatType<TicketSaleStat>("ticket_sale",
                            "Ticket Sale",
                            stat -> new Pair<>(stat.getTicket().getTypeId(), 1.0))
                            .also(REVENUE, TicketSaleStat::getRevenue)
            );

    /**
     * Represents the customer enter station statistical type for tracking customer entry data at each station.
     * The customer entry value is associated with the station name, and the count is always 1.
     */
    public static final StatType<CustomerEnterStationEvent, IndexedStatAggregate> CUSTOMER_ENTER_STATION =
            register(
                    new IndexedStatType<>("customer_enter_station",
                            "Customer Enter Station",
                            e -> new Pair<>(e.getStation(), 1.0))
            );

    /**
     * Represents the customer exit station statistical type for tracking customer exit data at each station.
     * The customer exit value is associated with the station name, and the count is always 1.
     */
    public static final StatType<CustomerEnterStationEvent, IndexedStatAggregate> CUSTOMER_EXIT_STATION =
            register(
                    new IndexedStatType<>("customer_exit_station",
                            "Customer Exit Station",
                            e -> new Pair<>(e.getStation(), 1.0))
            );

    /**
     * Represents the maintenance statistical type for tracking maintenance events.
     * The maintenance value is always 1, and the expenses are associated with the maintenance event.
     */
    public static final StatType<MaintenanceEvent, BasicStatAggregate> MAINTENANCE =
            register(
                    new BasicStatType<MaintenanceEvent>("maintenance",
                            "Maintenance",
                            e -> 1.0)
                            .also(EXPENSES, MaintenanceEvent::getExpense)
            );

    /**
     * Represents the emergencies statistical type for tracking subway emergency events.
     * The emergencies value is always 1.
     */
    public static final StatType<SubwayEmergencyEvent, BasicStatAggregate> EMERGENCIES =
            register(
                    new BasicStatType<>("emergencies",
                            "Emergencies",
                            e -> 1.0)
            );

    /** Register a new statistical type. */
    private static <T, A> StatType<T, A> register(StatType<T, A> type) {
        TYPES.add(type);
        return type;
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private Stats() {}

}
