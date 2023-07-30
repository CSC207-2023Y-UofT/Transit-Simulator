package stats.entry;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Maps the hierarchy of stats classes
 */
public class EntryHierarchy {

    private final Set<Class<? extends StatEntry>> mappedClasses = new HashSet<>();
    private final Map<Class<? extends StatEntry>, Set<Class<? extends StatEntry>>> hierarchy = new HashMap<>();

    public void map(Class<? extends StatEntry> entryClass) {

        if (mappedClasses.contains(entryClass)) return;
        mappedClasses.add(entryClass);

        List<Class<?>> parents = new ArrayList<>(List.of(entryClass.getInterfaces()));
        parents.add(entryClass.getSuperclass());
        parents.removeIf(Objects::isNull);
        parents.removeIf(c -> !StatEntry.class.isAssignableFrom(c));

        for (Class<?> inter : parents) {

            // Safe cast
            Class<? extends StatEntry> interAsStatEntry = inter.asSubclass(StatEntry.class);

            Set<Class<? extends StatEntry>> existing = hierarchy.getOrDefault(inter, new HashSet<>());
            existing.add(entryClass);
            hierarchy.put(interAsStatEntry, existing);

            map(interAsStatEntry);
        }

    }

    public <T extends StatEntry> List<Class<? extends T>> getLeafClasses(Class<T> entryClass) {
        map(entryClass);

        if (!hierarchy.containsKey(entryClass)) {
            return new ArrayList<>();
        }

        List<Class<? extends StatEntry>> pool = new ArrayList<>(List.of(entryClass));
        for (int i = 0; i < pool.size(); i++) {

            Class<? extends StatEntry> entry = pool.get(i);

            pool.addAll(getChildren(entry));

            boolean remove = Modifier.isAbstract(entry.getModifiers());
            remove |= Modifier.isInterface(entry.getModifiers());

            if (!remove) continue;

            pool.remove(i);
            i--;
        }

        List<Class<? extends T>> result = new ArrayList<>();

        for (Class<? extends StatEntry> clazz : pool) {

            // Always a safe cast due to the way the hierarchy is built
            result.add(clazz.asSubclass(entryClass));

        }

        return result;
    }

    private Set<Class<? extends StatEntry>> getChildren(Class<?> entryClass) {
        return hierarchy.getOrDefault(entryClass, new HashSet<>());
    }

    public List<Class<? extends StatEntry>> getAllLeafClasses() {
        return getLeafClasses(StatEntry.class);
    }
}
