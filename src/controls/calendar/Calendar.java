/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.calendar;

import controls.calendar.month.Month;
import controls.calendar.week.Week;
import dao.AppointmentDao;
import events.AppointmentEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Locale;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import models.Appointment;

import static java.util.Objects.requireNonNull;

/**
 * A control which handles the display and manipulation of calendar data.
 *
 * @author maxbrown
 */
public class Calendar extends Control {

    public enum ViewType {
        WEEK,
        MONTH
    }

    private final AppointmentDao appointmentDao = new AppointmentDao();

    private final Month month;

    private final Week week;

    public Calendar() {
        String stylesheet = getClass().getResource("calendar.css").toExternalForm();
        getStylesheets().add(stylesheet);

        refreshAppointments();

        this.week = new Week(this);
        this.month = new Month(this);

        viewTypeProperty().addListener((obs, oldViewType, newViewType) -> {
            switch (newViewType) {
                case WEEK:
                    setSelectedView(week);
                    break;
                case MONTH:
                    setSelectedView(month);
                    break;
            }
        });

        // Add appointment event filters
        addEventFilter(AppointmentEvent.NEW_APPOINTMENT, event -> {
            newAppointment(event.getAppointment());
        });

        addEventFilter(AppointmentEvent.EDIT_APPOINTMENT, event -> {
            editAppointment(event.getAppointment());
        });

        addEventFilter(AppointmentEvent.DELETE_APPOINTMENT, event -> {
            deleteAppointment(event.getAppointment());
        });
    }

    private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     * The list of {@link Appointment}s that are displayed within the
     * {@link Calendar}.
     *
     * @return The list of appointments.
     */
    public final ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    private final ObjectProperty<ZoneId> zoneId = new SimpleObjectProperty<>(this, "zoneId", ZoneId.systemDefault());

    /**
     * The time zone that should be used by the {@link Calendar}. Defaults to
     * {@link ZoneId#systemDefault()}.
     *
     * @return The time zone property.
     */
    public final ObjectProperty<ZoneId> zoneIdProperty() {
        return zoneId;
    }

    /**
     * Returns the value of the {@link #zoneIdProperty()}.
     *
     * @return The time zone.
     */
    public final ZoneId getZoneId() {
        return zoneIdProperty().get();
    }

    /**
     * Sets the value of the {@link #zoneIdProperty()}.
     *
     * @param zoneId The time zone to set.
     */
    public final void setZoneId(ZoneId zoneId) {
        requireNonNull(zoneId);
        zoneIdProperty().set(zoneId);
    }

    private final ObjectProperty<WeekFields> weekFields = new SimpleObjectProperty<>(this, "weekFields", WeekFields.of(Locale.getDefault()));

    /**
     * The week fields that should be used by the {@link Calendar} to determine
     * the correct first day of the week. Defaults to {@link WeekFields#ISO}.
     *
     * @return The week fields property.
     */
    public final ObjectProperty<WeekFields> weekFieldsProperty() {
        return weekFields;
    }

    /**
     * Gets the value of the {@link #weekFieldsProperty()}.
     *
     * @return The week fields.
     */
    public final WeekFields getWeekFields() {
        return weekFieldsProperty().get();
    }

    /**
     * Returns the first day of the week based upon the value of
     * {@link #weekFieldsProperty()}.
     *
     * @return The first day of the week.
     */
    public final DayOfWeek getFirstDayOfWeek() {
        return getWeekFields().getFirstDayOfWeek();
    }

    /**
     * Sets the value of the {@link #weekFieldsProperty()}.
     *
     * @param weekFields The week fields to set.
     */
    public final void setWeekFields(WeekFields weekFields) {
        requireNonNull(weekFields);
        weekFieldsProperty().set(weekFields);
    }

    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(this, "date", LocalDate.now());

    /**
     * The date that should be displayed by the {@link Calendar}. Defaults to
     * {@link LocalDate#now()}.
     *
     * @return The date property.
     */
    public final ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    /**
     * Returns the value of the {@link #dateProperty()}.
     *
     * @return The date to display.
     */
    public final LocalDate getDate() {
        return dateProperty().get();
    }

    /**
     * Sets the value of the {@link #dateProperty()}.
     *
     * @param date The date to set.
     */
    public final void setDate(LocalDate date) {
        requireNonNull(date);
        dateProperty().set(date);
    }

    private final ObjectProperty<LocalTime> time = new SimpleObjectProperty(this, "time", LocalTime.now());

    /**
     * The time that should be displayed by the {@link Calendar}. Defaults to
     * {@link LocalTime#now()}.
     *
     * @return The time property.
     */
    public final ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    /**
     * Returns the value of the {@link #timeProperty()}.
     *
     * @return The time to display.
     */
    public final LocalTime getTime() {
        return timeProperty().get();
    }

    /**
     * Sets the value of the {@link #timeProperty()}.
     *
     * @param time The time to set.
     */
    public final void setTime(LocalTime time) {
        requireNonNull(time);
        timeProperty().set(time);
    }

    public final ObjectProperty<ViewType> viewType = new SimpleObjectProperty<>(this, "viewType", ViewType.MONTH);

    /**
     * The {@link ViewType} that should be displayed by the {@link Calendar}.
     * Defaults to {@link ViewType#MONTH}.
     *
     * @return The view type property.
     */
    public final ObjectProperty<ViewType> viewTypeProperty() {
        return viewType;
    }

    /**
     * Returns the value of the {@link Calendar#viewTypeProperty()}.
     *
     * @return The view type.
     */
    public final ViewType getViewType() {
        return viewTypeProperty().get();
    }

    /**
     * Sets the value of the {@link Calendar#viewTypeProperty()}.
     *
     * @param viewType The view type.
     */
    public final void setViewType(ViewType viewType) {
        requireNonNull(viewType);
        viewTypeProperty().set(viewType);
    }

    private final ObjectProperty<Control> selectedView = new SimpleObjectProperty<>(this, "selectedView", new Month(this));

    /**
     * The selected {@link Control} that should be displayed by the
     * {@link Calendar}. Defaults to the {@link Month}.
     *
     * @return The selected view property.
     */
    public final ObjectProperty<Control> selectedViewProperty() {
        return selectedView;
    }

    /**
     * Returns the value of the {@link Calendar#selectedViewProperty()}.
     *
     * @return The selected view.
     */
    public final Control getSelectedView() {
        return selectedViewProperty().get();
    }

    /**
     * Sets the {@link Calendar#selectedViewProperty()}.
     *
     * @param view The view to select.
     */
    public final void setSelectedView(Control view) {
        requireNonNull(view);
        selectedViewProperty().set(view);
    }

    /**
     * Increments the {@link Calendar#date} forward by a certain amount based
     * upon the {@link Calendar#viewTypeProperty()}.
     */
    public final void goForward() {
        switch (getViewType()) {
            case WEEK:
                setDate(getDate().plusWeeks(1));
                break;
            case MONTH:
                setDate(getDate().plusMonths(1));
                break;
        }
    }

    /**
     * Decrements the {@link Calendar#date} forward by a certain amount based
     * upon the {@link Calendar#viewTypeProperty()}.
     */
    public final void goBackward() {
        switch (getViewType()) {
            case WEEK:
                setDate(getDate().minusWeeks(1));
                break;
            case MONTH:
                setDate(getDate().minusMonths(1));
                break;
        }
    }

    /**
     * Sets the {@link Calendar#date} to today's date.
     */
    public final void goToday() {
        setDate(LocalDate.now());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new CalendarSkin(this);
    }

    private void refreshAppointments() {
        // Get all appointments and add them to the calendar
        getAppointments().clear();
        appointmentDao.findAll().forEach(oa -> oa.ifPresent(a -> getAppointments().add(a)));
    }

    private void newAppointment(Appointment appointment) {
        appointmentDao.add(appointment);
        refreshAppointments();
    }

    private void editAppointment(Appointment appointment) {
        appointmentDao.update(appointment);
        refreshAppointments();
    }

    private void deleteAppointment(Appointment appointment) {
        appointmentDao.delete(appointment.getId());
        refreshAppointments();
    }

}
