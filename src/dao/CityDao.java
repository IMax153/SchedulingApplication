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
                    "SELECT * FROM city, country WHERE city.cityId = ?"
            );

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                City city = getCityFromResultSet(rs);
                return Optional.of(city);
            }
        } catch (SQLException sqle) {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Updates a city in the database.
     *
     * @param city The city to update.
     * @return True if the city was added successfully, otherwise false.
     */
    @Override
    public boolean update(City city) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Deletes a city from the database.
     *
     * @param id The unique identifier of the city to delete.
     * @return True if the city was added successfully, otherwise false.
     */
    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

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
