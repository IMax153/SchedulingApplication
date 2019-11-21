/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;
import utilities.Database;

/**
 * The abstract data access object from which all other data access objects
 * should derive.
 *
 * @author mab90
 */
public abstract class Dao<Entity> {

    /**
     * The connection to the UCertify MySQL database.
     */
    protected final Connection connection = Database.getConnection();

    public abstract Optional<Entity> findById(int id);

    public abstract List<Optional<Entity>> findAll();

    public abstract boolean add(Entity entity);

    public abstract boolean update(Entity entity);

    public abstract boolean delete(int id);

}
