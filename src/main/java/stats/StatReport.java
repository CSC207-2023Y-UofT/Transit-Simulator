package stats;

import java.util.Map;
import java.util.Optional;

public class StatReport<A> {
    private final Map<Long, A> data;

    public StatReport(Map<Long, A> data) {
        this.data = data;
    }

    public Map<Long, A> getData() {
        return data;
    }

    public Optional<A> getAggregate(long minute) {
        return Optional.ofNullable(data.get(minute));
    }
}
