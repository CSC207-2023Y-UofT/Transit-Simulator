package stats.entry;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntryHierarchyTest {

    public interface TestEntry extends StatEntry {}
    public interface TestSubEntry extends TestEntry {}
    public interface TestSubEntry2 extends TestEntry {}
    public static class TestEntryImpl implements TestEntry {}
    public static class TestSubEntryImpl implements TestSubEntry {}
    public static class TestSubEntryImpl2 extends TestEntryImpl {}
    public static class TestDoubleInheritance implements TestSubEntry, TestSubEntry2 {}

    @Test
    public void testHierarchy() {
        EntryHierarchy hierarchy = new EntryHierarchy();
        hierarchy.map(TestSubEntryImpl2.class);
        List<Class<? extends StatEntry>> all = hierarchy.getInheritors(StatEntry.class);
        assertEquals(2, all.size());
    }

    @Test
    public void testHierarchy2() {
        EntryHierarchy hierarchy = new EntryHierarchy();
        hierarchy.map(TestSubEntryImpl.class);
        List<Class<? extends TestSubEntry>> all = hierarchy.getInheritors(TestSubEntry.class);
        assertEquals(1, all.size());
    }

    @Test
    public void testHierarchy3() {
        EntryHierarchy hierarchy = new EntryHierarchy();
        hierarchy.map(TestDoubleInheritance.class);
        assertEquals(1, hierarchy.getInheritors(TestSubEntry.class).size());
        assertEquals(1, hierarchy.getInheritors(TestSubEntry2.class).size());
    }
}