/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import interfaces.Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import models.User;
import utilities.Database;

/**
 *
 * @author mab90
 */
public class UserDao implements Dao<User> {

    private Connection connection = Database.getConnection();

    @Override
    public Optional<User> findById(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM user AS u WHERE u.userId = ?"
            );

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getInt("active") == 1,
                        rs.getString("createdBy"),
                        rs.getString("lastUpdateBy"),
                        rs.getTimestamp("createDate").toInstant(),
                        rs.getTimestamp("lastUpdate").toInstant()
                );

                return Optional.of(user);
            }
        } catch (SQLException sqle) {
        }

        return Optional.empty();
    }

    @Override
    public List<Optional<User>> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(User entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
