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
 * Handles create, read, update, and delete operations for the address entity.
 *
 * @author mab90
 */
public class AddressDao extends Dao<Address> {

    /**
     * Gets an address specified by a unique identifier.
     *
     * @param id The unique identifier of the address to find.
     * @return An optional address.
     */
    @Override
    public Optional<Address> findById(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM address AS a "
                    + "INNER JOIN city as ci "
                    + "ON a.cityId = ci.cityId "
                    + "INNER JOIN country AS co "
                    + "ON ci.countryId = co.countryId "
                    + "WHERE a.addressId = ?"
            );

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Address address = getAddressFromResultSet(rs);
                return Optional.of(address);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Gets all addresses from the database.
     *
     * @return A list of optional addresses.
     */
    @Override
    public List<Optional<Address>> findAll() {
        List<Optional<Address>> addresses = new ArrayList<>();

        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM address AS a "
                    + "INNER JOIN city as ci "
                    + "ON a.cityId = ci.cityId "
                    + "INNER JOIN country AS co "
                    + "ON ci.countryId = co.countryId"
            );

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Address address = getAddressFromResultSet(rs);
                addresses.add(Optional.of(address));
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return addresses;
    }

    /**
     * Adds an address to the database.
     *
     * @param address The address to add.
     * @return True if the address was added successfully, otherwise false.
     */
    @Override
    public boolean add(Address address) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) "
                    + "VALUES (?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?"
            );

            pst.setString(1, address.getStreet());
            pst.setString(2, address.getAdditionalInformation());
            pst.setInt(3, address.getCity().getId());
            pst.setString(4, address.getPostalCode());
            pst.setString(5, address.getPhone());
            pst.setString(6, address.getCreatedBy());
            pst.setString(7, address.getUpdatedBy());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Updates an address in the database.
     *
     * @param address The address to update.
     * @return True if the address was updated successfully, otherwise false.
     */
    @Override
    public boolean update(Address address) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "UPDATE address AS a "
                    + "SET address = ?, address2 = ?, cityId = ?, postalCode = ?, phone = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ?) "
                    + "WHERE a.addressId = ?"
            );

            pst.setString(1, address.getStreet());
            pst.setString(2, address.getAdditionalInformation());
            pst.setInt(3, address.getCity().getId());
            pst.setString(4, address.getPostalCode());
            pst.setString(5, address.getPhone());
            pst.setString(6, address.getUpdatedBy());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Deletes an address from the database.
     *
     * @param id The unique identifier of the address to delete.
     * @return True if the address was deleted successfully, otherwise false.
     */
    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "DELETE FROM address AS a WHERE a.addressId = ?"
            );

            pst.setInt(1, id);

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Creates an Address object from the specified result set.
     *
     * @param rs The result set to extract the address from.
     * @return A new Address object.
     * @throws SQLException
     */
    private Address getAddressFromResultSet(ResultSet rs) throws SQLException {
        Country country = new Country(
                rs.getInt("co.countryId"),
                rs.getString("co.country"),
                rs.getString("co.createdBy"),
                rs.getString("co.lastUpdateBy"),
                rs.getTimestamp("co.createDate").toInstant(),
                rs.getTimestamp("co.lastUpdate").toInstant()
        );

        City city = new City(
                rs.getInt("ci.cityId"),
                rs.getString("ci.city"),
                country,
                rs.getString("ci.createdBy"),
                rs.getString("ci.lastUpdateBy"),
                rs.getTimestamp("ci.createDate").toInstant(),
                rs.getTimestamp("ci.lastUpdate").toInstant()
        );

        return new Address(
                rs.getInt("a.addressId"),
                rs.getString("a.address"),
                rs.getString("a.address2"),
                rs.getString("a.postalCode"),
                rs.getString("a.phone"),
                city,
                rs.getString("a.createdBy"),
                rs.getString("a.lastUpdateBy"),
                rs.getTimestamp("a.createDate").toInstant(),
                rs.getTimestamp("a.lastUpdate").toInstant()
        );
    }

}
