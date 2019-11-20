/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Represents a data access object for a particular entity.
 *
 * @author mab90
 */
public interface Dao<Entity> {

    /**
     * Gets an entity specified by a unique identifier.
     *
     * @param id
     * @return
     */
    public Optional<Entity> findById(int id);

    /**
     * Gets all entities of the specified type.
     *
     * @return
     */
    public List<Optional<Entity>> findAll();

    /**
     * Adds an entity to the database
     *
     * @param entity
     * @return
     */
    public boolean add(Entity entity);

    /**
     * Updates an entity within the database.
     *
     * @param entity
     * @return
     */
    public boolean update(Entity entity);

    /**
     * Deletes an entity specified by a unique identifier.
     *
     * @param id
     * @return
     */
    public boolean delete(int id);
}
