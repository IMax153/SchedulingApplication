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
 * Handles create, read, update, and delete operations for the user entity.
 *
 * @author mab90
 */
public class UserDao extends Dao<User> {

    /**
     * Gets a user specified by a unique identifier.
     *
     * @param id - The unique identifier of the user to find.
     * @return Optional<User>
     */
    @Override
    public Optional<User> findById(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM user AS u WHERE u.userId = ?"
            );

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                User user = getUserFromResultSet(rs);
                return Optional.of(user);
            }
        } catch (SQLException sqle) {
        }

        return Optional.empty();
    }

    /**
     * Gets all users from the database.
     *
     * @return List<Optional<User>>
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
        }

        return users;
    }

    /**
     * Adds a user to the database
     *
     * @param user - The user to add.
     * @return Boolean
     */
    @Override
    public boolean add(User user) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "INSERT INTO user (userName, password, isActive, createdBy, createDate, lastUpdateBy, lastUpdate) "
                    + "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP"
            );

            pst.setString(1, user.getUserName());
            pst.setString(2, user.getPassword());
            pst.setInt(3, user.getIsActive() ? 1 : 0);
            pst.setString(4, user.getCreatedBy());
            pst.setString(5, user.getUpdatedBy());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
        }

        return false;
    }

    /**
     * Updates a user in the database.
     *
     * @param user - The user to update.
     * @return Boolean
     */
    @Override
    public boolean update(User user) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "UPDATE user AS u "
                    + "SET userName = ?, password = ?, isActive = ?, createdBy = ?, lastUpdateBy = ?, lastUpdate = CURRENT_TIMESTAMP) "
                    + "WHERE u.userId = ?"
            );

            pst.setString(1, user.getUserName());
            pst.setString(2, user.getPassword());
            pst.setInt(3, user.getIsActive() ? 1 : 0);
            pst.setString(4, user.getCreatedBy());
            pst.setString(5, user.getUpdatedBy());
            pst.setInt(6, user.getId());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
        }

        return false;
    }

    /**
     * Deletes a user specified by a unique identifier.
     *
     * @param id - The unique identifier of the user to delete.
     * @return Boolean
     */
    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "DELETE FROM user AS u WHERE u.userId = ?"
            );

            pst.setInt(1, id);

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
        }

        return false;
    }

    /**
     * Authenticates a user with the scheduling application.
     *
     * @param userName - The userName of the user.
     * @param password - The password of the user
     * @return Optional<User>
     */
    public Optional<User> authenticateUser(String userName, String password) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM user AS u "
                    + "WHERE u.userName = ? AND u.password = ?"
            );

            pst.setString(1, userName);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                User user = getUserFromResultSet(rs);
                return Optional.of(user);
            }
        } catch (SQLException sqle) {
        }

        return Optional.empty();
    }

    /**
     * Creates a User object from the specified result set.
     *
     * @param rs - The result set to extract the user from.
     * @return User
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
