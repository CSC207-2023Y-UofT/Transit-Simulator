package stats.entry;

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

        List<Class<?>> superclasses = new ArrayList<>(List.of(entryClass.getInterfaces()));
        superclasses.removeIf(c -> !StatEntry.class.isAssignableFrom(c));

        for (Class<?> superclass : superclasses) {

            // Safe cast
            Class<? extends StatEntry> asSubclass = superclass.asSubclass(StatEntry.class);

            Set<Class<? extends StatEntry>> existing = hierarchy.getOrDefault(superclass, new HashSet<>());
            existing.add(entryClass);
            hierarchy.put(asSubclass, existing);

            map(asSubclass);
        }

        Class<?> superclass = entryClass.getSuperclass();
        if (superclass != null && StatEntry.class.isAssignableFrom(superclass)) {
            // Safe cast
            map(superclass.asSubclass(StatEntry.class));
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
            Set<Class<? extends StatEntry>> children = getChildren(entry);

            if (children.isEmpty()) {
                continue; // Leaf encountered, leave it in there
            }

            // Not a leaf, remove it and add its children
            pool.addAll(children);
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
}
