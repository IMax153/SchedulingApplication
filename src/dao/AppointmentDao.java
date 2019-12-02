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
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.util.Pair;
import models.*;
import utilities.DateUtils;

/**
 * Handles create, read, update, and delete operations for {@link Appointment}s.
 *
 * @author mab90
 */
public class AppointmentDao extends Dao<Appointment> {

    /**
     * Gets an {@link Appointment} by id.
     *
     * @param id The id of the appointment.
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
     * Gets all {@link Appointment}s.
     *
     * @return The list of appointments.
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
     * Gets all {@link Appointment}s for the specified {@link User}.
     *
     * @param user The user whose appointments should be fetched.
     * @return The list of appointments.
     */
    public List<Optional<Appointment>> findAllForUser(User user) {
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
                    + "ON ci.countryId = co.countryId "
                    + "WHERE ap.userId = ?"
            );

            pst.setInt(1, user.getId());

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

    public List<Optional<Pair<String, Integer>>> findTypesForUser(User user, YearMonth yearMonth) {
        List<Optional<Pair<String, Integer>>> types = new ArrayList<>();

        try {
            PreparedStatement pst = connection.prepareStatement(
                    "SELECT type, COUNT(*) AS `count` FROM appointment "
                    + "WHERE userId = ? AND MONTH(createDate) = ? AND YEAR(createDate) = ? "
                    + "GROUP BY type"
            );

            pst.setInt(1, user.getId());
            pst.setInt(2, yearMonth.getMonthValue());
            pst.setInt(3, yearMonth.getYear());

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String type = rs.getString("type");
                int count = rs.getInt("count");
                types.add(Optional.of(new Pair<>(type, count)));
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return types;
    }

    /**
     * Adds an {@link Appointment}.
     *
     * @param appointment The appointment to add.
     * @return True if the appointment was added successfully, otherwise false.
     */
    @Override
    public boolean add(Appointment appointment) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "INSERT INTO appointment (customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)"
            );

            pst.setInt(1, appointment.getCustomer().getId());
            pst.setInt(2, appointment.getUser().getId());
            pst.setString(3, appointment.getTitle());
            pst.setString(4, appointment.getDescription());
            pst.setString(5, appointment.getLocation());
            pst.setString(6, appointment.getContact());
            pst.setString(7, appointment.getType());
            pst.setString(8, appointment.getUrl());
            pst.setTimestamp(9, Timestamp.from(appointment.getInterval().getZonedStartDateTime().toInstant()));
            pst.setTimestamp(10, Timestamp.from(appointment.getInterval().getZonedEndDateTime().toInstant()));
            pst.setString(11, appointment.getCreatedBy());
            pst.setString(12, appointment.getUpdatedBy());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Updates an {@link Appointment}.
     *
     * @param appointment The appointment to update.
     * @return True if the appointment was updated successfully, otherwise
     * false.
     */
    @Override
    public boolean update(Appointment appointment) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "UPDATE appointment "
                    + "SET customerId = ?, userId = ?, title = ?, description = ?, location = ?, contact = ?, type = ?, url = ?, start = ?, end = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? "
                    + "WHERE appointmentId = ?"
            );

            pst.setInt(1, appointment.getCustomer().getId());
            pst.setInt(2, appointment.getUser().getId());
            pst.setString(3, appointment.getTitle());
            pst.setString(4, appointment.getDescription());
            pst.setString(5, appointment.getLocation());
            pst.setString(6, appointment.getContact());
            pst.setString(7, appointment.getType());
            pst.setString(8, appointment.getUrl());
            pst.setTimestamp(9, Timestamp.from(appointment.getInterval().getZonedStartDateTime().toInstant()));
            pst.setTimestamp(10, Timestamp.from(appointment.getInterval().getZonedEndDateTime().toInstant()));
            pst.setString(11, appointment.getUpdatedBy());
            pst.setInt(12, appointment.getId());

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Deletes an {@link Appointment}.
     *
     * @param id The id of the address to delete.
     * @return True if the appointment was deleted successfully, otherwise
     * false.
     */
    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement pst = connection.prepareStatement(
                    "DELETE FROM appointment WHERE appointmentId = ?"
            );

            pst.setInt(1, id);

            return pst.executeUpdate() == 1;
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }

        return false;
    }

    /**
     * Creates an {@link Appointment} from the specified {@link ResultSet}.
     *
     * @param rs The result set.
     * @return The appointment.
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

        Interval interval = new Interval(
                rs.getTimestamp("ap.start").toLocalDateTime().atZone(DateUtils.DEFAULT_ZONE_ID),
                rs.getTimestamp("ap.end").toLocalDateTime().atZone(DateUtils.DEFAULT_ZONE_ID)
        );

        return new Appointment(
                rs.getInt("ap.appointmentId"),
                rs.getString("ap.title"),
                rs.getString("ap.description"),
                rs.getString("ap.location"),
                rs.getString("ap.contact"),
                rs.getString("ap.type"),
                rs.getString("ap.url"),
                interval,
                customer,
                user,
                rs.getString("ap.createdBy"),
                rs.getString("ap.lastUpdateBy"),
                rs.getTimestamp("ap.createDate").toInstant(),
                rs.getTimestamp("ap.lastUpdate").toInstant()
        );
    }
}
