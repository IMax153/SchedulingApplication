/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import models.User;

/**
 * Handles create, read, update, and delete operations for {@link User}s.
 *
 * @author mab90
 */
public class UserDao extends Dao<User> {

    /**
     * Gets a {@link User} by id.
     *
     * @param id The id of the user.
     * @return The user.
     */
    @Override
    public Optional<User> findById(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM user WHERE userId = ?"
            );

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                User user = getUserFromResultSet(rs);
                return Optional.of(user);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Gets all {@link User}s.
     *
     * @return The list of users.
     */
    @Override
    public List<Optional<User>> findAll() {
        List<Optional<User>> users = new ArrayList<>();

        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM user"
            );

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                User user = getUserFromResultSet(rs);
                users.add(Optional.of(user));
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return users;
    }

    /**
     * Adds a {@link User}.
     *
     * @param user The user to add.
     * @return True if the user was added successfully, otherwise false.
     */
    @Override
    public boolean add(User user) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "INSERT INTO user (userName, password, isActive, createdBy, createDate, lastUpdateBy, lastUpdate) "
                    + "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP)"
            );

            pst.setString(1, user.getUserName());
            pst.setString(2, user.getPassword());
            pst.setInt(3, user.isActive() ? 1 : 0);
            pst.setString(4, user.getCreatedBy());
            pst.setString(5, user.getUpdatedBy());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Updates a {@link User}.
     *
     * @param user The user to update.
     * @return True if the user was updated successfully, otherwise false.
     */
    @Override
    public boolean update(User user) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "UPDATE user "
                    + "SET userName = ?, password = ?, isActive = ?, createdBy = ?, lastUpdateBy = ?, lastUpdate = CURRENT_TIMESTAMP "
                    + "WHERE userId = ?"
            );

            pst.setString(1, user.getUserName());
            pst.setString(2, user.getPassword());
            pst.setInt(3, user.isActive() ? 1 : 0);
            pst.setString(4, user.getCreatedBy());
            pst.setString(5, user.getUpdatedBy());
            pst.setInt(6, user.getId());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Deletes a {@link User}.
     *
     * @param id The id of the user to delete.
     * @return True if the user was deleted successfully, otherwise false.
     */
    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "DELETE FROM user WHERE userId = ?"
            );

            pst.setInt(1, id);

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Authenticates a {@link User}.
     *
     * @param userName The userName of the user.
     * @param password The password of the user
     * @return An optional user.
     */
    public Optional<User> authenticateUser(String userName, String password) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM user "
                    + "WHERE userName = ? AND password = ?"
            );

            pst.setString(1, userName);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                User user = getUserFromResultSet(rs);
                return Optional.of(user);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Creates an {@link User} from the specified {@link ResultSet}.
     *
     * @param rs The result set.
     * @return The user.
     * @throws SQLException
     */
    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("userId"),
                rs.getString("userName"),
                rs.getString("password"),
                rs.getInt("active") == 1,
                rs.getString("createdBy"),
                rs.getString("lastUpdateBy"),
                rs.getTimestamp("createDate").toInstant(),
                rs.getTimestamp("lastUpdate").toInstant()
        );
    }

}
