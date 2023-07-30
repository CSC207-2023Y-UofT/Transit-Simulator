package stats.entry;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntryHierarchyTest {

    public interface TestEntry extends StatEntry {}
    public interface TestSubEntry extends TestEntry {}
    public class TestEntryImpl implements TestEntry {}
    public class TestSubEntryImpl implements TestSubEntry {}

    @Test
    public void testHierarchy() {
        EntryHierarchy hierarchy = new EntryHierarchy();
        hierarchy.map(TestSubEntry.class);
        hierarchy.map(TestEntry.class);
        List<Class<? extends StatEntry>> all = hierarchy.getLeafClasses(StatEntry.class);
        assertEquals(2, all.size());
    }
}