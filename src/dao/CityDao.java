/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import models.*;

/**
 * Handles create, read, update, and delete operations for the city entity.
 *
 * @author mab90
 */
public class CityDao extends Dao<City> {

    /**
     * Gets a city specified by a unique identifier.
     *
     * @param id The unique identifier of the city to find.
     * @return An optional city.
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
     * Gets all cities from the database.
     *
     * @return A list of optional cities.
     */
    @Override
    public List<Optional<City>> findAll() {
        List<Optional<City>> cities = new ArrayList<>();

        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM city, country"
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
     * Adds a city to the database.
     *
     * @param city The city to add.
     * @return True if the city was added successfully, otherwise false.
     */
    @Override
    public boolean add(City city) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) "
                    + "VALUES (?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?"
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
     * Updates a city in the database.
     *
     * @param city The city to update.
     * @return True if the city was added successfully, otherwise false.
     */
    @Override
    public boolean update(City city) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "UPDATE city AS c "
                    + "SET city = ?, countryId = ?, createdBy = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ?) "
                    + "WHERE c.cityId = ?"
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
     * Deletes a city from the database.
     *
     * @param id The unique identifier of the city to delete.
     * @return True if the city was added successfully, otherwise false.
     */
    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "DELETE FROM city AS c WHERE c.cityId = ?"
            );

            pst.setInt(1, id);

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Creates a City object from the specified result set.
     *
     * @param rs The result set to extract the city from.
     * @return A new city object.
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
                rs.getString("city.createdBy"),
                rs.getString("city.lastUpdateBy"),
                rs.getTimestamp("city.createDate").toInstant(),
                rs.getTimestamp("city.lastUpdate").toInstant(),
                country
        );
    }

}
