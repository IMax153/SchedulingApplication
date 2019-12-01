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
import models.*;

/**
 * Handles create, read, update, and delete operations for {@link City}s.
 *
 * @author mab90
 */
public class CityDao extends Dao<City> {

    /**
     * Gets a {@link City} by id.
     *
     * @param id The id of the city.
     * @return The city.
     */
    @Override
    public Optional<City> findById(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM city "
                    + "INNER JOIN country "
                    + "ON city.countryId = country.countryId "
                    + "WHERE city.cityId = ?"
            );

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                City city = getCityFromResultSet(rs);
                return Optional.of(city);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Gets a {@link City} by name.
     *
     * @param name The name of the city.
     * @return The city.
     */
    public Optional<City> findByName(String name) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM city "
                    + "INNER JOIN country "
                    + "ON city.countryId = country.countryId "
                    + "WHERE city.city = ?"
            );

            pst.setString(1, name);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                City city = getCityFromResultSet(rs);
                return Optional.of(city);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Gets all {@link City}s.
     *
     * @return The list of cities.
     */
    @Override
    public List<Optional<City>> findAll() {
        List<Optional<City>> cities = new ArrayList<>();

        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM city "
                    + "INNER JOIN country "
                    + "ON city.countryId = country.countryId"
            );

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                City city = getCityFromResultSet(rs);
                cities.add(Optional.of(city));
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return cities;
    }

    /**
     * Adds a {@link City}.
     *
     * @param city The city to add.
     * @return True if the city was added successfully, otherwise false.
     */
    @Override
    public boolean add(City city) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) "
                    + "VALUES (?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)"
            );

            pst.setString(1, city.getName());
            pst.setInt(2, city.getCountry().getId());
            pst.setString(3, city.getCreatedBy());
            pst.setString(4, city.getUpdatedBy());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Updates a {@link City}.
     *
     * @param city The city to update.
     * @return True if the city was added successfully, otherwise false.
     */
    @Override
    public boolean update(City city) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "UPDATE city "
                    + "SET city = ?, countryId = ?, createdBy = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? "
                    + "WHERE cityId = ?"
            );

            pst.setString(1, city.getName());
            pst.setInt(2, city.getCountry().getId());
            pst.setString(3, city.getCreatedBy());
            pst.setString(4, city.getUpdatedBy());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Deletes a {@link City}.
     *
     * @param id The id of the city to delete.
     * @return True if the city was added successfully, otherwise false.
     */
    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "DELETE FROM city WHERE cityId = ?"
            );

            pst.setInt(1, id);

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Checks if a {@link City} with the specified name exists in the database.
     *
     * @param name The name of the city to check.
     * @return True if the city exists in the database, otherwise false.
     */
    public boolean exists(String name) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT COUNT(cityId) FROM city WHERE city = ?;"
            );

            pst.setString(1, name);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Creates a {@link City} from the specified {@link ResultSet}.
     *
     * @param rs The result set.
     * @return The city.
     * @throws SQLException
     */
    private City getCityFromResultSet(ResultSet rs) throws SQLException {
        Country country = new Country(
                rs.getInt("country.countryId"),
                rs.getString("country.country"),
                rs.getString("country.createdBy"),
                rs.getString("country.lastUpdateBy"),
                rs.getTimestamp("country.createDate").toInstant(),
                rs.getTimestamp("country.lastUpdate").toInstant()
        );

        return new City(
                rs.getInt("city.cityId"),
                rs.getString("city.city"),
                country,
                rs.getString("city.createdBy"),
                rs.getString("city.lastUpdateBy"),
                rs.getTimestamp("city.createDate").toInstant(),
                rs.getTimestamp("city.lastUpdate").toInstant()
        );
    }

}
