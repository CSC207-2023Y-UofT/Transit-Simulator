package stats.entry;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntryHierarchyTest {

    public interface TestEntry extends StatEntry {}
    public interface TestSubEntry extends TestEntry {}
    public interface TestSubEntry2 extends TestEntry {}
    public class TestEntryImpl implements TestEntry {}
    public class TestSubEntryImpl implements TestSubEntry {}
    public class TestSubEntryImpl2 extends TestEntryImpl {}

    public class TestDoubleInheritance implements TestSubEntry, TestSubEntry2 {}

    @Test
    public void testHierarchy() {
        EntryHierarchy hierarchy = new EntryHierarchy();
        hierarchy.map(TestSubEntryImpl2.class);
        List<Class<? extends StatEntry>> all = hierarchy.getLeafClasses(StatEntry.class);
        assertEquals(2, all.size());
    }

    @Test
    public void testHierarchy2() {
        EntryHierarchy hierarchy = new EntryHierarchy();
        hierarchy.map(TestSubEntryImpl.class);
        List<Class<? extends TestSubEntry>> all = hierarchy.getLeafClasses(TestSubEntry.class);
        assertEquals(1, all.size());
    }

    @Test
    public void testHierarchy3() {
        EntryHierarchy hierarchy = new EntryHierarchy();
        hierarchy.map(TestDoubleInheritance.class);
        assertEquals(1, hierarchy.getLeafClasses(TestSubEntry.class).size());
        assertEquals(1, hierarchy.getLeafClasses(TestSubEntry2.class).size());
    }
}