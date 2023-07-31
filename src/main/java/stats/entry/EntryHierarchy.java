package stats.entry;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Maps the hierarchy of stats classes
 */
public class EntryHierarchy {

    /**
     * The set of classes that have already been mapped. This is use
     */
    private final Set<Class<? extends StatEntry>> mappedClasses = new HashSet<>();

    /**
     * A map of stat entry classes to sets of their children.
     */
    private final Map<Class<? extends StatEntry>, Set<Class<? extends StatEntry>>> hierarchy = new HashMap<>();

    /**
     * Map the hierarchy of stat entry classes that have this entry class
     * as a descendant.
     * @param entryClass The stat entry class to map.
     */
    public void map(Class<? extends StatEntry> entryClass) {

        // If the class has already been mapped, we
        // don't need to map it again.
        if (mappedClasses.contains(entryClass)) return;
        mappedClasses.add(entryClass);

        // Get the parents of the entry class, this will be its
        // implemented interfaces, and its superclass if one exists.
        List<Class<?>> parents = new ArrayList<>(List.of(entryClass.getInterfaces()));
        parents.add(entryClass.getSuperclass());
        parents.removeIf(Objects::isNull); // Don't include nulls (null superclass)
        parents.removeIf(c -> !StatEntry.class.isAssignableFrom(c)); // Don't include non-stat entry classes

        // Go through the parents, and set the entry class as a child of each
        // parent. Then, recursively, map the parent.
        for (Class<?> inter : parents) {

            // Safe cast, we know that the parent is a stat entry
            Class<? extends StatEntry> interAsStatEntry = inter.asSubclass(StatEntry.class);

            // Get the existing children of the parent, and add the entry class
            Set<Class<? extends StatEntry>> existing = hierarchy.getOrDefault(inter, new HashSet<>());
            existing.add(entryClass);
            hierarchy.put(interAsStatEntry, existing);

            // Recurse
            map(interAsStatEntry);
        }

    }

    /**
     * Get all the classes that are concrete implementations of the given entry class.
     * @param entryClass The entry class to get the implementations of.
     * @return A list of all the concrete implementations of the given entry class.
     * @param <T> The type of the entry class.
     */
    public <T extends StatEntry> List<Class<? extends T>> getInheritors(Class<T> entryClass) {
        map(entryClass);

        if (!hierarchy.containsKey(entryClass)) {
            boolean isConcrete = !Modifier.isAbstract(entryClass.getModifiers());
            isConcrete &= !Modifier.isInterface(entryClass.getModifiers());

            if (isConcrete) return List.of(entryClass);
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

    /**
     * Utility method to get the children of a given entry class, or a default empty set.
     */
    private Set<Class<? extends StatEntry>> getChildren(Class<?> entryClass) {
        return hierarchy.getOrDefault(entryClass, new HashSet<>());
    }

    /**
     * Get all the leaf classes of the stat entry hierarchy.
     * This is used for storage.
     */
    public List<Class<? extends StatEntry>> getAllLeafClasses() {
        return getInheritors(StatEntry.class);
    }

}
