
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
import models.Country;

/**
 * Handles create, read, update, and delete operations for the country entity.
 *
 * @author mab90
 */
public class CountryDao extends Dao<Country> {

    /**
     * Gets a country specified by a unique identifier.
     *
     * @param id - The unique identifier of the country to find.
     * @return Optional<Country>
     */
    @Override
    public Optional<Country> findById(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM country AS c WHERE c.countryId = ?"
            );

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Country country = getCountryFromResultSet(rs);
                return Optional.of(country);
            }
        } catch (SQLException sqle) {
        }

        return Optional.empty();
    }

    /**
     * Gets all countries from the database.
     *
     * @return List<Optional<Country>>
     */
    @Override
    public List<Optional<Country>> findAll() {
        List<Optional<Country>> countries = new ArrayList<>();

        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM country"
            );

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Country country = getCountryFromResultSet(rs);
                countries.add(Optional.of(country));
            }
        } catch (SQLException sqle) {
        }

        return countries;
    }

    /**
     * Adds a country to the database
     *
     * @param country - The country to add.
     * @return Boolean
     */
    @Override
    public boolean add(Country country) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) "
                    + "VALUES (?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)"
            );

            pst.setString(1, country.getName());
            pst.setString(2, country.getCreatedBy());
            pst.setString(3, country.getUpdatedBy());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
        }

        return false;
    }

    /**
     * Updates a country in the database.
     *
     * @param country - The country to update.
     * @return Boolean
     */
    @Override
    public boolean update(Country country) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "UPDATE country AS c "
                    + "SET country = ?, createdBy = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ?"
                    + "WHERE c.countryId = ?"
            );

            pst.setString(1, country.getName());
            pst.setString(2, country.getCreatedBy());
            pst.setString(3, country.getUpdatedBy());
            pst.setInt(4, country.getId());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
        }

        return false;
    }

    /**
     * Deletes a country specified by a unique identifier.
     *
     * @param id - The unique identifier of the country to delete.
     * @return Boolean
     */
    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "DELETE FROM country AS c WHERE c.countryId = ?"
            );

            pst.setInt(1, id);

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
        }

        return false;
    }

    /**
     * Creates a Country object from the specified result set.
     *
     * @param rs - The result set to extract the country from.
     * @return Country
     * @throws SQLException
     */
    private Country getCountryFromResultSet(ResultSet rs) throws SQLException {
        return new Country(
                rs.getInt("countryId"),
                rs.getString("country"),
                rs.getString("createdBy"),
                rs.getString("lastUpdateBy"),
                rs.getTimestamp("createDate").toInstant(),
                rs.getTimestamp("lastUpdate").toInstant()
        );
    }
}
