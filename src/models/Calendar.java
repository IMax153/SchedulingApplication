/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utilities.DateUtils;

/**
 * Class representing a calendar within the application.
 *
 * @author mab90
 */
public class Calendar {

    /**
     * The name of the calendar.
     */
    private final String calendarName;

    /**
     * The current date that the calendar is set to.
     */
    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();

    /**
     * The current month that the calendar is set to.
     */
    private final StringProperty month = new SimpleStringProperty();

    /**
     * The appointments that the calendar contains.
     */
    private final List<Appointment> appointments;

    public Calendar(String calendarName) {
        this.calendarName = calendarName;
        this.appointments = new ArrayList<>();
        this.date.set(DateUtils.getToday());
        this.month.set(date.get().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));

        this.date.addListener((obs, oldVal, newVal) -> {
            this.month.set(newVal.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        });
    }

    /**
     * Gets the name of the calendar.
     *
     * @return The name of the calendar.
     */
    public final String getCalendarName() {
        return calendarName;
    }

    /**
     * Gets the month property of the calendar.
     *
     * @return The month property of the calendar.
     */
    public final StringProperty monthProperty() {
        return month;
    }

    /**
     * Gets the current month that the calendar is set to.
     *
     * @return The current month that the calendar is set to.
     */
    public final String getMonth() {
        return monthProperty().get();
    }

    /**
     * The date property of the calendar.
     *
     * @return The date property of the calendar.
     */
    public final ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    /**
     * Gets the date that the calendar is currently set to.
     *
     * @return The date that the calendar is currently set to.
     */
    public final LocalDate getDate() {
        return dateProperty().get();
    }

    /**
     * Sets the date that the calendar should be set to.
     *
     * @param date The date that the calendar should be set to.
     */
    public final void setDate(LocalDate date) {
        dateProperty().set(date);
    }

    /**
     * Adds an appointment to the calendar.
     *
     * @param appointment The appointment to add.
     */
    public final void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * Gets a list of appointments for the specified date.
     *
     * @param date The date whose appointments should be returned.
     * @return A list of appointments.
     */
    public final List<Appointment> getAppointmentsFor(LocalDate date) {
        return getAppointmentsFor(date.atStartOfDay(DateUtils.DEFAULT_ZONE_ID));
    }

    /**
     * Gets a list of appointments for the specified date.
     *
     * @param date The date whose appointments should be returned.
     * @return A list of appointments.
     */
    public final List<Appointment> getAppointmentsFor(ZonedDateTime date) {
        List<Appointment> appts = new ArrayList<>();

        this.appointments.stream().filter(a -> {
            return a.getInterval().contains(date);
        }).forEachOrdered(a -> {
            appts.add(a);
        });

        return appts;
    }

    /**
     * Gets a list of appointments for the specified interval.
     *
     * @param interval The interval whose appointments should be returned.
     * @return A list of appointments.
     */
    public final List<Appointment> getAppointmentsBetween(Interval interval) {
        List<Appointment> appts = new ArrayList<>();

        this.appointments.stream().filter(a -> {
            return interval.overlapsWith(a.getInterval());
        }).forEachOrdered(a -> {
            appts.add(a);
        });

        return appts;
    }

}
