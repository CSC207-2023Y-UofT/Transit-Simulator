package stats.aggregator.average;

import stats.StatAggregator;
import stats.StatAggregatorRef;
import stats.StatEntry;

import java.util.List;
import java.util.function.Function;

public class AverageAggregator<T extends StatEntry>
        extends StatAggregatorRef<T, AverageAggregate> {
    private final Function<T, Double> supplier;

    public AverageAggregator(Function<T, Double> supplier) {
        this.supplier = supplier;
    }

    @Override
    public AverageAggregate aggregateStats(List<T> entries) {
        checkNotEmpty(entries);

        double sum = 0;
        for (T entry : entries) {
            sum += supplier.apply(entry);
        }
        return new AverageAggregate(entries.size(), sum / entries.size());
    }

    @Override
    public AverageAggregate aggregateExisting(List<AverageAggregate> aggregates) {
        checkNotEmpty(aggregates);

        AverageAggregate aggregate = aggregates.get(0);
        for (int i = 1; i < aggregates.size(); i++) {
            aggregate = combineTwo(aggregate, aggregates.get(i));
        }

        return aggregate;
    }

    private AverageAggregate combineTwo(AverageAggregate one, AverageAggregate two) {
        int count = one.getCount() + two.getCount();
        double mod1 = one.getCount() / (double) count;
        double mod2 = two.getCount() / (double) count;
        double comp1 = one.value() * mod1;
        double comp2 = two.value() * mod2;
        double average = comp1 + comp2;
        return new AverageAggregate(count, average);
    }
}
