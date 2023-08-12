package persistence.boundary;

import java.util.List;
import java.util.Optional;

public interface IntIndexedDataStore<T> {
    /**
     * Saves the entity to the data store.
     * @param entity The entity to save.
     */
    void save(T entity);

    /**
     * Finds the entity with the given id.
     * @param id The id of the entity to find.
     * @return The entity with the given id, or empty if no such entity exists.
     */
    Optional<T> find(int id);

    /**
     * Returns all entities in the data store.
     */
    List<T> findAll();

    /**
     * Returns true if an entity with the given id exists in the data store.
     * @param id The id of the entity to check.
     * @return True if an entity with the given id exists in the data store.
     */
    boolean existsById(int id);

    /**
     * Deletes the entity with the given id.
     * @param id The id of the entity to delete.
     */
    void delete(int id);

    /**
     * Deletes all entities in the data store.
     */
    void deleteAll();
}
