/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
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
     * The current {@link LocalDate} that the calendar is set to.
     */
    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();

    /**
     * The current {@link YearMonth} that the calendar is set to.
     */
    private final ObjectProperty<YearMonth> yearMonth = new SimpleObjectProperty<>();

    /**
     * The current name of the month that the calendar is set to.
     */
    private final StringProperty yearMonthDisplay = new SimpleStringProperty();

    /**
     * The appointments that the calendar contains.
     */
    private final List<Appointment> appointments;

    public Calendar(String calendarName) {
        this.calendarName = calendarName;
        this.appointments = new ArrayList<>();
        this.date.set(DateUtils.getToday());
        this.yearMonth.set(YearMonth.of(date.get().getYear(), date.get().getMonth()));
        this.yearMonthDisplay.set(getYearMonthDisplay(this.dateProperty().get()));

        this.date.addListener((obs, oldVal, newVal) -> {
            this.yearMonth.set(YearMonth.of(date.get().getYear(), date.get().getMonth()));
            this.yearMonthDisplay.set(getYearMonthDisplay(this.dateProperty().get()));
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
     * Gets the {@link YearMonth} property of the calendar.
     *
     * @return The {@link YearMonth} property of the calendar.
     */
    public final ObjectProperty<YearMonth> yearMonthProperty() {
        return yearMonth;
    }

    /**
     * Gets the current {@link YearMonth} that the calendar is set to.
     *
     * @return The current {@link YearMonth} that the calendar is set to.
     */
    public final YearMonth getYearMonth() {
        return yearMonthProperty().get();
    }

    /**
     * Gets the current display property for the {@link YearMonth} that the
     * calendar is set to.
     *
     * @return The current display property for the {@link YearMonth} that the
     * calendar is set to.
     */
    public final StringProperty yearMonthDisplayProperty() {
        return yearMonthDisplay;
    }

    /**
     * Gets the current {@link Month} that the calendar is set to.
     *
     * @return The current {@link Month} that the calendar is set to.
     */
    public final Month getMonth() {
        return yearMonthProperty().get().getMonth();
    }

    /**
     * Gets the name of the current month that the calendar is set to.
     *
     * @return The name of the current month that the calendar is set to.
     */
    public final String getMonthName() {
        return yearMonthProperty().get().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
    }

    /**
     * Gets the current year that the calendar is set to.
     *
     * @return The current year that the calendar is set to.
     */
    public final int getYear() {
        return yearMonthProperty().get().getYear();
    }

    /**
     * The {@link LocalDate} property of the calendar.
     *
     * @return The {@link LocalDate} property of the calendar.
     */
    public final ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    /**
     * Gets the {@link LocalDate} that the calendar is currently set to.
     *
     * @return The {@link LocalDate} that the calendar is currently set to.
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
        List<Appointment> appts = new ArrayList<>();

        this.appointments.stream().filter(a -> {
            LocalDate startDate = a.getInterval().getStartDate();
            LocalDate endDate = a.getInterval().getEndDate();
            return startDate.equals(date) || endDate.equals(date);
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

    private final String getYearMonthDisplay(LocalDate date) {
        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        int year = date.getYear();
        return month + ", " + year;
    }

}
