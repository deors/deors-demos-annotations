package deors.demos.annotations.entity;

/**
 * A generic base data access interface, parameterizing the type
 * of the entity and the type of the key.
 *
 * @param <T> the type of the entity bean
 * @param <K> the type of the primary key
 *
 * @author deors
 * @version 1.0
 */
public interface BaseDataAccess<T, K> {

    /**
     * Selects a bean by its id.
     *
     * @param id the id of the bean to be searched for in the data store
     * @return the found bean
     */
    T selectById(K id);

    /**
     * Inserts a new bean in the data store.
     *
     * @param newRecord the bean to be inserted in the data store
     * @return the bean including any enhancement or persistencia information (if any)
     */
    T insert(T newRecord);

    /**
     * Updates an existing bean in the data store.
     *
     * @param updatedRecord the bean to be updated in the data store
     */
    void update(T updatedRecord);

    /**
     * Deletes an existing bean in the data store.
     *
     * @param deletedRecord the bean to be deleted in the data store
     */
    void delete(T deletedRecord);
}
