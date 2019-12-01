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
 * Handles create, read, update, and delete operations for {@link Customer}s.
 *
 * @author mab90
 */
public class CustomerDao extends Dao<Customer> {

    /**
     * Gets a {@link Customer} by id.
     *
     * @param id The id of the customer.
     * @return The customer.
     */
    @Override
    public Optional<Customer> findById(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM customer AS cu "
                    + "INNER JOIN address AS a "
                    + "ON cu.addressId = a.addressId "
                    + "INNER JOIN city AS ci "
                    + "ON a.cityId = ci.cityId "
                    + "INNER JOIN country AS co "
                    + "ON ci.countryId = co.countryId "
                    + "WHERE cu.customerId = ?"
            );

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Customer customer = getCustomerFromResultSet(rs);
                return Optional.of(customer);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Gets a {@link Customer} by name.
     *
     * @param name The name of the customer.
     * @return The customer.
     */
    public Optional<Customer> findByName(String name) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM customer AS cu "
                    + "INNER JOIN address AS a "
                    + "ON cu.addressId = a.addressId "
                    + "INNER JOIN city AS ci "
                    + "ON a.cityId = ci.cityId "
                    + "INNER JOIN country AS co "
                    + "ON ci.countryId = co.countryId "
                    + "WHERE cu.customerName = ?"
            );

            pst.setString(1, name);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Customer customer = getCustomerFromResultSet(rs);
                return Optional.of(customer);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Gets all {@link Customer}s.
     *
     * @return The list of customers.
     */
    @Override
    public List<Optional<Customer>> findAll() {
        List<Optional<Customer>> customers = new ArrayList<>();

        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM customer AS cu "
                    + "INNER JOIN address AS a "
                    + "ON cu.addressId = a.addressId "
                    + "INNER JOIN city AS ci "
                    + "ON a.cityId = ci.cityId "
                    + "INNER JOIN country AS co "
                    + "ON ci.countryId = co.countryId"
            );

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Customer customer = getCustomerFromResultSet(rs);
                customers.add(Optional.of(customer));
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return customers;
    }

    /**
     * Adds a {@link Customer}.
     *
     * @param customer The customer to add.
     * @return True if the customer was added successfully, otherwise false.
     */
    @Override
    public boolean add(Customer customer) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) "
                    + "VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)"
            );

            pst.setString(1, customer.getName());
            pst.setInt(2, customer.getAddress().getId());
            pst.setInt(3, customer.isActive() ? 1 : 0);
            pst.setString(4, customer.getCreatedBy());
            pst.setString(5, customer.getUpdatedBy());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Updates a {@link Customer}.
     *
     * @param customer The customer to update.
     * @return True if the customer was updated successfully, otherwise false.
     */
    @Override
    public boolean update(Customer customer) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "UPDATE customer "
                    + "SET customerName = ?, addressId = ?, active = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? "
                    + "WHERE customerId = ?"
            );

            pst.setString(1, customer.getName());
            pst.setInt(2, customer.getAddress().getId());
            pst.setInt(3, customer.isActive() ? 1 : 0);
            pst.setString(4, customer.getUpdatedBy());
            pst.setInt(5, customer.getId());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Deletes a {@link Customer}.
     *
     * @param id The id of the customer.
     * @return True if the customer was deleted successfully, otherwise false.
     */
    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "DELETE FROM customer WHERE customerId = ?"
            );

            pst.setInt(1, id);

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Creates a {@link Customer} from the specified {@link ResultSet}.
     *
     * @param rs The result set.
     * @return The customer.
     * @throws SQLException
     */
    private Customer getCustomerFromResultSet(ResultSet rs) throws SQLException {
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

        Address address = new Address(
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

        return new Customer(
                rs.getInt("cu.customerId"),
                rs.getString("cu.customerName"),
                rs.getInt("cu.active") == 1,
                address,
                rs.getString("cu.createdBy"),
                rs.getString("cu.lastUpdateBy"),
                rs.getTimestamp("cu.createDate").toInstant(),
                rs.getTimestamp("cu.lastUpdate").toInstant()
        );
    }

}
