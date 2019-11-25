/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.calendar;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import models.Appointment;
import utilities.DateUtils;

/**
 * Represents a calendar within the application.
 *
 * @author mab90
 */
public class Calendar {

    /**
     * Represents the calendar type.
     */
    public enum CalendarType {
        MONTHLY,
        WEEKLY
    }

    /**
     * A property containing the name of the calendar.
     */
    private final StringProperty name = new SimpleStringProperty();

    /**
     * A property containing the type of the calendar.
     */
    private final ObjectProperty<CalendarType> calendarType = new SimpleObjectProperty();

    /**
     * A property containing a list of {@link Appointment}s contained within the
     * calendar.
     */
    private final ListProperty<Appointment> appointments = new SimpleListProperty<>();

    /**
     * A property containing the current {@link LocalDate} of the calendar.
     */
    private final ObjectProperty<LocalDate> currentDate = new SimpleObjectProperty<>();

    /**
     * A property containing the current {@link YearMonth} of the calendar.
     */
    private final ObjectProperty<YearMonth> currentYearMonth = new SimpleObjectProperty();

    /**
     * A property containing the current display month for the calendar.
     */
    private final StringProperty displayMonth = new SimpleStringProperty();

    /**
     * A property containing the current display year for the calendar.
     */
    private final IntegerProperty displayYear = new SimpleIntegerProperty();

    public Calendar(String name) {
        this.name.set(name);
        this.appointments.set(FXCollections.observableArrayList());
        this.calendarType.set(CalendarType.MONTHLY);
        this.currentDate.set(DateUtils.getToday());
        this.currentYearMonth.set(YearMonth.of(this.currentDate.get().getYear(), this.currentDate.get().getMonth()));
        this.displayMonth.set(this.currentDate.get().getMonth().getDisplayName(TextStyle.FULL, DateUtils.DEFAULT_LOCALE));
        this.displayYear.set(this.currentDate.get().getYear());

        this.currentDate.addListener((obs, oldValue, newValue) -> {
            this.currentYearMonth.set(YearMonth.of(newValue.getYear(), newValue.getMonth()));
            this.displayMonth.set(newValue.getMonth().getDisplayName(TextStyle.FULL, DateUtils.DEFAULT_LOCALE));
            this.displayYear.set(newValue.getYear());
        });
    }

    /**
     * Gets the property containing the {@link Calendar#name}.
     *
     * @return The calendar name property.
     */
    public final StringProperty nameProperty() {
        return name;
    }

    /**
     * Gets the {@link Calendar#name}.
     *
     * @return The calendar name.
     */
    public final String getName() {
        return nameProperty().get();
    }

    /**
     * Gets the property containing the {@link Calendar#calendarType}.
     *
     * @return The calendar type property.
     */
    public final ObjectProperty<CalendarType> calendarTypeProperty() {
        return calendarType;
    }

    /**
     * Gets the {@link Calendar#calendarType}.
     *
     * @return The calendar type.
     */
    public final CalendarType getCalendarType() {
        return calendarTypeProperty().get();
    }

    /**
     * Sets the {@link Calendar#calendarType}.
     *
     * @param calendarType The calendar type to set.
     */
    public final void setCalendarType(CalendarType calendarType) {
        calendarTypeProperty().set(calendarType);
    }

    /**
     * Gets the property containing the {@link Calendar#appointments}.
     *
     * @return The appointments property.
     */
    public final ListProperty<Appointment> appointmentsProperty() {
        return appointments;
    }

    /**
     * Gets the {@link Calendar#appointments}.
     *
     * @return The list of appointments.
     */
    public final List<Appointment> getAppointments() {
        return appointmentsProperty().get();
    }

    /**
     * Gets the {@link Calendar#appointments} for the specified date.
     *
     * @return The list of appointments.
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
     * Adds an appointment to the {@link Calendar#appointments}.
     *
     * @param appointment The appointment to add.
     */
    public final void addAppointment(Appointment appointment) {
        appointmentsProperty().get().add(appointment);
    }

    /**
     * Gets the property containing the {@link Calendar#currentDate}.
     *
     * @return The current date property.
     */
    public final ObjectProperty<LocalDate> currentDateProperty() {
        return currentDate;
    }

    /**
     * Gets the {@link Calendar#currentDate}.
     *
     * @return The current date.
     */
    public final LocalDate getCurrentDate() {
        return currentDateProperty().get();
    }

    /**
     * Gets the property containing the {@link Calendar#currentYearMonth}.
     *
     * @return The current year and month property.
     */
    public final ObjectProperty<YearMonth> currentYearMonthProperty() {
        return currentYearMonth;
    }

    /**
     * Gets the {@link Calendar#currentYearMonth}.
     *
     * @return The current year and month.
     */
    public final YearMonth getCurrentYearMonth() {
        return currentYearMonthProperty().get();
    }

    /**
     * Gets the property containing the {@link Calendar#displayMonth}.
     *
     * @return The display month property.
     */
    public final StringProperty displayMonthProperty() {
        return displayMonth;
    }

    /**
     * Gets the {@link Calendar#displayMonth}.
     *
     * @return The display month.
     */
    public final String getDisplayMonth() {
        return displayMonthProperty().get();
    }

    /**
     * Gets the property containing the {@link Calendar#displayYear}.
     *
     * @return The display year property.
     */
    public final IntegerProperty displayYearProperty() {
        return displayYear;
    }

    /**
     * Gets the {@link Calendar#displayYear}.
     *
     * @return The display year.
     */
    public final Integer getDisplayYear() {
        return displayYearProperty().get();
    }

    /**
     * Gets the first {@link LocalDate} in the week containing the
     * {@link Calendar#currentDate}. Respects locality with respect to which day
     * is considered the start of the week.
     *
     * @return The first day of the week.
     */
    public final LocalDate getFirstDayOfWeek() {
        return DateUtils.getFirstDayOfWeek(getCurrentDate());
    }

    /**
     * Gets the {@link LocalDate}s in the week containing the
     * {@link Calendar#currentDate} beginning with the first day of the week.
     * Respects locality with respect to which day is considered the start of
     * the week.
     *
     * @return The list of dates.
     */
    public final List<LocalDate> getDatesOfWeek() {
        List<LocalDate> dates = new ArrayList<>();

        // Get the first date of the current week
        LocalDate date = getFirstDayOfWeek();

        for (int day = 0; day < 7; day++) {
            dates.add(date);
            date = date.plusDays(1);
        }

        return dates;
    }

    /**
     * Gets the first {@link LocalDate} in the first week of the month
     * containing the {@link Calendar#currentDate}. Respects locality with
     * respect to which day is considered the start of the week.
     *
     * @return The list of dates.
     */
    public final LocalDate getFirstDayOfMonth() {
        return DateUtils.getFirstDayOfWeek(DateUtils.getFirstDayOfMonth(getCurrentDate()));
    }

    /**
     * Gets the {@link LocalDate}s in the month containing the
     * {@link Calendar#currentDate} beginning with the first date in the first
     * week of the month. Respects locality with respect to which day is
     * considered the start of the week.
     *
     * @return The list of dates.
     */
    public final List<LocalDate> getDatesOfMonth() {
        List<LocalDate> dates = new ArrayList<>();

        // Get the first date of the first week of the current month
        LocalDate date = getFirstDayOfMonth();

        // A calendar is typically viewed as a grid of 6 rows representing the weeks of the month
        for (int week = 0; week < 6; week++) {
            // and 7 columns representing the days of each week
            for (int day = 0; day < 7; day++) {
                // The date of
                dates.add(date);
                date = date.plusDays(1);
            }
        }

        return dates;
    }

    /**
     * Advances the {@link Calendar#currentDate} forward based upon the
     * {@link Calendar#calendar}.
     */
    public void goForward() {
        switch (getCalendarType()) {
            case WEEKLY: {
                addWeeks(1);
                break;
            }
            case MONTHLY: {
                addMonths(1);
                break;
            }
        }
    }

    /**
     * Advances the {@link Calendar#currentDate} forward based upon the
     * {@link Calendar#calendar}.
     */
    public void goBackward() {
        switch (getCalendarType()) {
            case WEEKLY: {
                subtractWeeks(1);
                break;
            }
            case MONTHLY: {
                subtractMonths(1);
                break;
            }
        }
    }

    /**
     * Add the specified number of weeks to the {@link Calendar#currentDate}.
     *
     * @param numberOfWeeks The number of weeks to add.
     */
    private void addWeeks(Integer numberOfWeeks) {
        currentDateProperty().set(currentDateProperty().get().plusWeeks(numberOfWeeks));
    }

    /**
     * Add the specified number of months to the {@link Calendar#currentDate}.
     *
     * @param numberOfMonths The number of weeks to add.
     */
    private void addMonths(Integer numberOfMonths) {
        currentDateProperty().set(currentDateProperty().get().plusMonths(numberOfMonths));
    }

    /**
     * Subtract the specified number of weeks to the
     * {@link Calendar#currentDate}.
     *
     * @param numberOfWeeks The number of weeks to subtract.
     */
    private void subtractWeeks(Integer numberOfWeeks) {
        currentDateProperty().set(currentDateProperty().get().minusWeeks(numberOfWeeks));
    }

    /**
     * Subtract the specified number of months to the
     * {@link Calendar#currentDate}.
     *
     * @param numberOfMonths The number of weeks to subtract.
     */
    private void subtractMonths(Integer numberOfMonths) {
        currentDateProperty().set(currentDateProperty().get().minusMonths(numberOfMonths));
    }

}
