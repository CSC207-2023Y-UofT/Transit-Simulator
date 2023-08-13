package app_business.interactor;

import app_business.boundary.IStatInteractor;
import stats.StatTracker;
import stats.aggregate.ExpenseAggregate;
import stats.aggregate.RevenueAggregate;
import stats.aggregator.StatAggregator;
import stats.aggregator.impl.ExpenseAggregator;
import stats.aggregator.impl.RevenueAggregator;
import stats.entry.StatEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Interactor class responsible for operations related to statistics.
 * Implements the {@link IStatInteractor} interface.
 */
public class StatInteractor implements IStatInteractor {

    /**
     * Data controller responsible for stats-related data management.
     */
    private final StatTracker stats;

    /**
     * Constructs a StatInteractor instance.
     *
     * @param stats The statistics data controller.
     */
    public StatInteractor(StatTracker stats) {
        this.stats = stats;
    }

    /**
     * Returns a list of aggregates of the given type, one for each minute in the given horizon.
     *
     * @param horizonMinutes   The horizon in minutes.
     * @param aggregator       The aggregator to use.
     * @param defaultAggregate The default aggregate to use if no aggregate is found for a given minute.
     * @param <A>              The type of entries this aggregator operates on, which must extend {@link StatEntry}.
     * @param <B>              The type of aggregates this aggregator produces, which must be {@link Serializable}.
     * @return A list of aggregates.
     */
    private <A extends StatEntry, B extends Serializable> List<B> getAggregates(long horizonMinutes,
                                                                                StatAggregator<A, B> aggregator,
                                                                                B defaultAggregate) {
        long currIndex = stats.getIndexingStrategy().getTimeIndex();

        List<B> aggregatesList = new ArrayList<>();

        Map<Long, B> aggregateMap = stats.getOrAggregate(aggregator,
                currIndex - horizonMinutes,
                currIndex);

        stats.aggregateCurrent(aggregator).ifPresent(a -> aggregateMap.put(currIndex, a));

        for (long i = currIndex - horizonMinutes; i <= currIndex; i++) {
            Optional<B> aggregate = Optional.ofNullable(aggregateMap.get(i));

            aggregate.ifPresentOrElse(aggregatesList::add, () -> aggregatesList.add(defaultAggregate));
        }
        return aggregatesList;
    }

    @Override
    public List<RevenueAggregate> getRevenue(long horizon) {
        return getAggregates(horizon, new RevenueAggregator(), new RevenueAggregate(0));
    }

    @Override
    public List<ExpenseAggregate> getExpenses(long horizon) {
        return getAggregates(horizon, new ExpenseAggregator(), new ExpenseAggregate(0));
    }
}
