package stats.timing;

public interface TimeIndexProvider {
    long getTimeIndex();
    long getTimeIndex(long epochTime);
}
