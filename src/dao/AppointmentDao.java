/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import models.*;

/**
 * Handles create, read, update, and delete operations for the user entity.
 *
 * @author mab90
 */
public class AppointmentDao extends Dao<Appointment> {

    /**
     * Gets an appointment specified by a unique identifier.
     *
     * @param id The unique identifier of the appointment to find.
     * @return An optional appointment.
     */
    @Override
    public Optional<Appointment> findById(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM appointment as ap "
                    + "INNER JOIN customer AS cu "
                    + "ON ap.customerId = cu.customerId "
                    + "INNER JOIN user as u "
                    + "ON ap.userId = u.userId "
                    + "INNER JOIN address AS ad "
                    + "ON cu.addressId = ad.addressId "
                    + "INNER JOIN city AS ci "
                    + "ON ad.cityId = ci.cityId "
                    + "INNER JOIN country AS co "
                    + "ON ci.countryId = co.countryId "
                    + "WHERE ap.appointmentId = ?"
            );

            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Appointment appointment = getAppointmentFromResultSet(rs);
                return Optional.of(appointment);
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Gets all appointments from the database.
     *
     * @return A list of optional appointments.
     */
    @Override
    public List<Optional<Appointment>> findAll() {
        List<Optional<Appointment>> appointments = new ArrayList<>();

        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT * FROM appointment as ap "
                    + "INNER JOIN customer AS cu "
                    + "ON ap.customerId = cu.customerId "
                    + "INNER JOIN user as u "
                    + "ON ap.userId = u.userId "
                    + "INNER JOIN address AS ad "
                    + "ON cu.addressId = ad.addressId "
                    + "INNER JOIN city AS ci "
                    + "ON ad.cityId = ci.cityId "
                    + "INNER JOIN country AS co "
                    + "ON ci.countryId = co.countryId"
            );

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Appointment appointment = getAppointmentFromResultSet(rs);
                appointments.add(Optional.of(appointment));
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return appointments;
    }

    /**
     * Adds an appointment to the database.
     *
     * @param appointment The appointment to add.
     * @return True if the appointment was added successfully, otherwise false.
     */
    @Override
    public boolean add(Appointment appointment) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "INSERT INTO appointment (customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?"
            );

            pst.setInt(1, appointment.getCustomer().getId());
            pst.setInt(2, appointment.getUser().getId());
            pst.setString(3, appointment.getTitle());
            pst.setString(4, appointment.getDescription());
            pst.setString(5, appointment.getLocation());
            pst.setString(6, appointment.getContact());
            pst.setString(7, appointment.getType());
            pst.setString(8, appointment.getUrl());
            pst.setTimestamp(9, Timestamp.from(appointment.getStart()));
            pst.setTimestamp(10, Timestamp.from(appointment.getEnd()));
            pst.setString(11, appointment.getCreatedBy());
            pst.setString(12, appointment.getUpdatedBy());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Updates a appointment in the database.
     *
     * @param appointment The appointment to update.
     * @return True if the appointment was updated successfully, otherwise
     * false.
     */
    @Override
    public boolean update(Appointment appointment) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "UPDATE customer AS c "
                    + "SET customerId = ?, userId = ?, title = ?, description = ?, location = ?, contact = ?, type = ?, url = ?, start = ?, end = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ?) "
                    + "WHERE c.customerId = ?"
            );

            pst.setInt(1, appointment.getCustomer().getId());
            pst.setInt(2, appointment.getUser().getId());
            pst.setString(3, appointment.getTitle());
            pst.setString(4, appointment.getDescription());
            pst.setString(5, appointment.getLocation());
            pst.setString(6, appointment.getContact());
            pst.setString(7, appointment.getType());
            pst.setString(8, appointment.getUrl());
            pst.setTimestamp(9, Timestamp.from(appointment.getStart()));
            pst.setTimestamp(10, Timestamp.from(appointment.getEnd()));
            pst.setString(11, appointment.getUpdatedBy());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Deletes an appointment from the database.
     *
     * @param id The unique identifier of the appointment to delete.
     * @return True if the appointment was deleted successfully, otherwise
     * false.
     */
    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "DELETE FROM appointment AS a WHERE a.appointmentId = ?"
            );

            pst.setInt(1, id);

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Creates an Appointment object from the specified result set.
     *
     * @param rs The result set to extract the appointment from.
     * @return A new Appointment object.
     * @throws SQLException
     */
    private Appointment getAppointmentFromResultSet(ResultSet rs) throws SQLException {
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
                rs.getInt("ad.addressId"),
                rs.getString("ad.address"),
                rs.getString("ad.address2"),
                rs.getString("ad.postalCode"),
                rs.getString("ad.phone"),
                city,
                rs.getString("ad.createdBy"),
                rs.getString("ad.lastUpdateBy"),
                rs.getTimestamp("ad.createDate").toInstant(),
                rs.getTimestamp("ad.lastUpdate").toInstant()
        );

        Customer customer = new Customer(
                rs.getInt("cu.customerId"),
                rs.getString("cu.customerName"),
                rs.getInt("cu.active") == 1,
                address,
                rs.getString("cu.createdBy"),
                rs.getString("cu.lastUpdateBy"),
                rs.getTimestamp("cu.createDate").toInstant(),
                rs.getTimestamp("cu.lastUpdate").toInstant()
        );

        User user = new User(
                rs.getInt("u.userId"),
                rs.getString("u.userName"),
                rs.getString("u.password"),
                rs.getInt("u.active") == 1,
                rs.getString("cu.createdBy"),
                rs.getString("cu.lastUpdateBy"),
                rs.getTimestamp("cu.createDate").toInstant(),
                rs.getTimestamp("cu.lastUpdate").toInstant()
        );

        return new Appointment(
                rs.getInt("ap.appointmentId"),
                rs.getString("ap.title"),
                rs.getString("ap.description"),
                rs.getString("ap.location"),
                rs.getString("ap.contact"),
                rs.getString("ap.type"),
                rs.getString("ap.url"),
                rs.getTimestamp("ap.start").toInstant(),
                rs.getTimestamp("ap.end").toInstant(),
                customer,
                user,
                rs.getString("ap.createdBy"),
                rs.getString("ap.lastUpdateBy"),
                rs.getTimestamp("ap.createDate").toInstant(),
                rs.getTimestamp("ap.lastUpdate").toInstant()
        );
    }
}
