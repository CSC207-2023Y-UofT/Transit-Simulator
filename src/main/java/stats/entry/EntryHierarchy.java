package stats.entry;

import java.util.*;

public class EntryHierarchy {

    private final Set<Class<? extends StatEntry>> mappedClasses = new HashSet<>();
    private final Map<Class<? extends StatEntry>, Set<Class<? extends StatEntry>>> hierarchy = new HashMap<>();

    private void map(Class<? extends StatEntry> entryClass) {

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
        Set<Class<? extends StatEntry>> list = hierarchy.getOrDefault(entryClass, new HashSet<>());

        List<Class<? extends T>> result = new ArrayList<>();
        for (Class<? extends StatEntry> clazz : list) {
            // Always a safe cast due to the way the hierarchy is built
            result.add(clazz.asSubclass(entryClass));
        }

        return result;
    }
}
