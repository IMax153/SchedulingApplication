/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.form.appointment;

import application.SchedulingApplication;
import dao.AppointmentDao;
import dao.CustomerDao;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import javafx.util.Pair;
import java.util.Optional;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import models.Appointment;
import models.Customer;
import models.Interval;
import models.User;
import utilities.DateUtils;

import static java.util.Objects.requireNonNull;
import java.util.stream.Collectors;

/**
 * A {@link Control} used for creating and editing {@link Appointment}s.
 *
 * @author maxbrown
 */
public class AppointmentForm extends Control {

    public enum Mode {
        NEW,
        EDIT,
        DELETE
    }

    private Mode mode = Mode.NEW;

    private Appointment appointment;

    private final AppointmentDao appointmentDao = new AppointmentDao();

    public AppointmentForm() {
        String stylesheet = getClass().getResource("appointment-form.css").toExternalForm();
        getStylesheets().add(stylesheet);

        CustomerDao customerDao = new CustomerDao();
        customerDao.findAll().forEach(oc -> {
            oc.ifPresent(c -> {
                customers.add(c);
            });
        });
    }

    public AppointmentForm(Appointment appointment) {
        this();
        this.mode = Mode.EDIT;
        this.appointment = appointment;
        setInitialValues(appointment);
    }

    /**
     * Returns the value of {@link AppointmentForm#mode}.
     *
     * @return The appointment form mode.
     */
    public Mode getMode() {
        return mode;
    }

    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    /**
     * Returns the value of {@link AppointmentForm#customers}.
     *
     * @return The list of customers.
     */
    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    private final StringProperty title = new SimpleStringProperty(this, "title", "");

    /**
     * A property containing the value of the {@link Appointment#title}.
     *
     * @return The title property.
     */
    public final StringProperty titleProperty() {
        return title;
    }

    /**
     * Returns the value of {@link AppointmentForm#title}.
     *
     * @return The appointment title.
     */
    public final String getTitle() {
        return titleProperty().get();
    }

    /**
     * Sets the value of {@link AppointmentForm#title}.
     *
     * @param title The appointment title.
     */
    public final void setTitle(String title) {
        requireNonNull(title);
        titleProperty().set(title);
    }

    private final StringProperty type = new SimpleStringProperty(this, "type", "");

    /**
     * A property containing the value of the {@link Appointment#type}.
     *
     * @return The type property.
     */
    public final StringProperty typeProperty() {
        return type;
    }

    /**
     * Returns the value of {@link AppointmentForm#type}.
     *
     * @return The appointment type.
     */
    public final String getType() {
        return typeProperty().get();
    }

    /**
     * Sets the value of {@link AppointmentForm#type}.
     *
     * @param type The appointment type.
     */
    public final void setType(String type) {
        requireNonNull(type);
        typeProperty().set(type);
    }

    private final StringProperty location = new SimpleStringProperty(this, "location", "");

    /**
     * A property containing the value of the {@link Appointment#location}.
     *
     * @return The location property.
     */
    public final StringProperty locationProperty() {
        return location;
    }

    /**
     * Returns the value of {@link AppointmentForm#location}.
     *
     * @return The appointment location.
     */
    public final String getLocation() {
        return locationProperty().get();
    }

    /**
     * Sets the value of {@link AppointmentForm#location}.
     *
     * @param location The appointment location.
     */
    public final void setLocation(String location) {
        requireNonNull(location);
        locationProperty().set(location);
    }

    private final StringProperty description = new SimpleStringProperty(this, "description", "");

    /**
     * A property containing the value of the {@link Appointment#description}.
     *
     * @return The description property.
     */
    public final StringProperty descriptionProperty() {
        return description;
    }

    /**
     * Returns the value of {@link AppointmentForm#description}.
     *
     * @return The appointment description.
     */
    public final String getDescription() {
        return descriptionProperty().get();
    }

    /**
     * Sets the value of {@link AppointmentForm#description}.
     *
     * @param description The appointment description.
     */
    public final void setDescription(String description) {
        requireNonNull(description);
        descriptionProperty().set(description);
    }

    private final StringProperty contact = new SimpleStringProperty(this, "contact", "");

    /**
     * A property containing the value of the {@link Appointment#contact}.
     *
     * @return The contact property.
     */
    public final StringProperty contactProperty() {
        return contact;
    }

    /**
     * Returns the value of {@link AppointmentForm#contact}.
     *
     * @return The appointment contact.
     */
    public final String getContact() {
        return contactProperty().get();
    }

    /**
     * Sets the value of {@link AppointmentForm#contact}.
     *
     * @param contact The appointment contact.
     */
    public final void setContact(String contact) {
        requireNonNull(contact);
        contactProperty().set(contact);
    }

    private final StringProperty url = new SimpleStringProperty(this, "url", "");

    /**
     * A property containing the value of the {@link Appointment#url}.
     *
     * @return The URL property.
     */
    public final StringProperty urlProperty() {
        return url;
    }

    /**
     * Returns the value of {@link AppointmentForm#url}.
     *
     * @return The appointment URL.
     */
    public final String getUrl() {
        return urlProperty().get();
    }

    /**
     * Sets the value of {@link AppointmentForm#url}.
     *
     * @param url The appointment URL.
     */
    public final void setUrl(String url) {
        requireNonNull(url);
        urlProperty().set(url);
    }

    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(this, "startDate", DateUtils.getToday());

    /**
     * A property containing the start {@link LocalDate} of the
     * {@link Appointment#interval}.
     *
     * @return The date property.
     */
    public final ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    /**
     * Returns the value of {@link AppointmentForm#date}.
     *
     * @return The appointment date.
     */
    public final LocalDate getDate() {
        return dateProperty().get();
    }

    /**
     * Sets the value of {@link AppointmentForm#url}.
     *
     * @param date The appointment date.
     */
    public final void setDate(LocalDate date) {
        requireNonNull(date);
        dateProperty().set(date);
    }

    private final ObjectProperty<LocalTime> startTime = new SimpleObjectProperty<>(this, "startTime", DateUtils.getCurrentTime().truncatedTo(ChronoUnit.HOURS).plusHours(1));

    /**
     * A property containing the start {@link LocalTime} of the
     * {@link Appointment#interval}.
     *
     * @return The start time property.
     */
    public final ObjectProperty<LocalTime> startTimeProperty() {
        return startTime;
    }

    /**
     * Returns the value of {@link AppointmentForm#startTime}.
     *
     * @return The appointment start time.
     */
    public final LocalTime getStartTime() {
        return startTimeProperty().get();
    }

    /**
     * Sets the value of {@link AppointmentForm#startTime}.
     *
     * @param startTime The appointment start time.
     */
    public final void setStartTime(LocalTime startTime) {
        requireNonNull(startTime);
        startTimeProperty().set(startTime);
    }

    private final ObjectProperty<LocalTime> endTime = new SimpleObjectProperty<>(this, "endTime", DateUtils.getCurrentTime().truncatedTo(ChronoUnit.HOURS).plusHours(2));

    /**
     * A property containing the end {@link LocalTime} of the
     * {@link Appointment#interval}.
     *
     * @return The end time property.
     */
    public final ObjectProperty<LocalTime> endTimeProperty() {
        return endTime;
    }

    /**
     * Returns the value of {@link AppointmentForm#endTime}.
     *
     * @return The appointment end time.
     */
    public final LocalTime getEndTime() {
        return endTimeProperty().get();
    }

    /**
     * Sets the value of {@link AppointmentForm#endTime}.
     *
     * @param endTime The appointment end time.
     */
    public final void setEndTime(LocalTime endTime) {
        requireNonNull(endTime);
        endTimeProperty().set(endTime);
    }

    /**
     * Returns a new {@link Interval} constructed from the
     * {@link AppointmentForm#date},  {@link AppointmentForm#startTime}, and
     * {@link AppointmentForm#endTime}.
     *
     * @return The interval.
     */
    public final Interval getInterval() {
        ZonedDateTime startZonedDateTime = DateUtils.toZonedDateTime(getDate(), getStartTime());
        ZonedDateTime endZonedDateTime = DateUtils.toZonedDateTime(getDate(), getEndTime());

        return new Interval(startZonedDateTime, endZonedDateTime);
    }

    private final ObjectProperty<Customer> customer = new SimpleObjectProperty<>(this, "customer", null);

    /**
     * A property containing the value of the {@link Appointment#customer}.
     *
     * @return The customer property.
     */
    public final ObjectProperty<Customer> customerProperty() {
        return customer;
    }

    /**
     * Returns the value of {@link AppointmentForm#customer}.
     *
     * @return The appointment customer.
     */
    public final Customer getCustomer() {
        return customerProperty().get();
    }

    /**
     * Sets the value of {@link AppointmentForm#startTime}.
     *
     * @param customer The appointment customer.
     */
    public final void setCustomer(Customer customer) {
        requireNonNull(customer);
        customerProperty().set(customer);
    }

    private final StringProperty error = new SimpleStringProperty(this, "error", "");

    /**
     * A property containing the value of the {@link Appointment#error}.
     *
     * @return The error property.
     */
    public final StringProperty errorProperty() {
        return error;
    }

    /**
     * Returns the value of {@link AppointmentForm#error}.
     *
     * @return The appointment error.
     */
    public final String getError() {
        return errorProperty().get();
    }

    /**
     * Sets the value of {@link AppointmentForm#error}.
     *
     * @param error The appointment error.
     */
    public final void setError(String error) {
        requireNonNull(error);
        errorProperty().set(error);
    }

    private final BooleanProperty valid = new SimpleBooleanProperty(this, "valid", false);

    /**
     * A property containing the value of whether or not the
     * {@link AppointmentForm} is in a valid state.
     *
     * @return The valid property.
     */
    public final BooleanProperty validProperty() {
        return valid;
    }

    /**
     * Returns the value of the {@link AppointmentForm#validProperty()}.
     *
     * @return True if the form is in a valid state, otherwise false.
     */
    public final boolean isValid() {
        validateForm();
        return validProperty().get();
    }

    /**
     * Sets the value of the {@link AppointmentForm#validProperty()}.
     *
     * @param valid True if the form is in a valid state, otherwise false.
     */
    public final void setValid(boolean valid) {
        requireNonNull(valid);
        validProperty().set(valid);
    }

    /**
     * Populates the {@link AppoinmentForm} with initial values based upon the
     * values of the specified {@link Appointment}.
     *
     * @param appointment The appointment to use to populate the form.
     */
    public final void setInitialValues(Appointment appointment) {
        setTitle(appointment.getTitle());
        setType(appointment.getType());
        setLocation(appointment.getLocation());
        setDescription(appointment.getDescription());
        setContact(appointment.getContact());
        setUrl(appointment.getUrl());
        setDate(appointment.getInterval().getStartDate());
        setStartTime(appointment.getInterval().getStartTime());
        setEndTime(appointment.getInterval().getEndTime());
        setCustomer(appointment.getCustomer());
    }

    /**
     * Submits the {@link AppointmentForm} and returns the new or edited
     * {@link Appointment}.
     *
     * @return The appointment.
     */
    public Pair<Mode, Appointment> submit() {
        validateForm();

        if (isValid()) {
            User user = SchedulingApplication.USER;
            Interval interval = getInterval();

            switch (mode) {
                case NEW:
                    return new Pair(
                            mode,
                            new Appointment(
                                    -1,
                                    getTitle(),
                                    getDescription(),
                                    getLocation(),
                                    getContact(),
                                    getType(),
                                    getUrl(),
                                    interval,
                                    getCustomer(),
                                    user,
                                    user.getUserName(),
                                    user.getUserName(),
                                    Instant.now(),
                                    Instant.now()
                            )
                    );
                case EDIT:
                    return new Pair(
                            mode,
                            new Appointment(
                                    appointment.getId(),
                                    getTitle(),
                                    getDescription(),
                                    getLocation(),
                                    getContact(),
                                    getType(),
                                    getUrl(),
                                    interval,
                                    getCustomer(),
                                    user,
                                    user.getUserName(),
                                    user.getUserName(),
                                    appointment.getCreatedAt(),
                                    Instant.now()
                            )
                    );
                case DELETE:
                    return new Pair(
                            mode,
                            new Appointment(
                                    appointment.getId(),
                                    getTitle(),
                                    getDescription(),
                                    getLocation(),
                                    getContact(),
                                    getType(),
                                    getUrl(),
                                    interval,
                                    getCustomer(),
                                    user,
                                    user.getUserName(),
                                    user.getUserName(),
                                    appointment.getCreatedAt(),
                                    Instant.now()
                            )
                    );
                default:
                    break;
            }
        }

        return null;
    }

    public Pair<Mode, Appointment> delete() {
        mode = Mode.DELETE;
        return submit();
    }

    private void validateForm() {
        LocalTime workingHoursStart = LocalTime.of(9, 0);
        LocalTime workingHoursEnd = LocalTime.of(17, 0);

        List<Appointment> appointments = appointmentDao.findAllForUser(SchedulingApplication.USER)
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        // Filter out the appointment being edited if any
        if (appointment != null) {
            appointments = appointments.stream().filter(a -> a.getId() != appointment.getId()).collect(Collectors.toList());
        }

        boolean isIntersectingOtherAppointment = appointments.stream().anyMatch(a -> a.getInterval().overlaps(getInterval()));

        if (getTitle() == null || getTitle().isEmpty()) {
            setError("Please provide a title");
            setValid(false);
        } else if (getType() == null || getType().isEmpty()) {
            setError("Please specify an appointment type");
            setValid(false);
        } else if (getLocation() == null || getLocation().isEmpty()) {
            setError("Please specify a location");
            setValid(false);
        } else if (getDescription() == null || getDescription().isEmpty()) {
            setError("Please include a description");
            setValid(false);
        } else if (getContact() == null || getContact().isEmpty()) {
            setError("Please include a contact");
            setValid(false);
        } else if (getUrl() == null || getUrl().isEmpty()) {
            setError("Please include a URL");
            setValid(false);
        } else if (getDate() == null) {
            setError("Please select a date");
            setValid(false);
        } else if (getStartTime() == null) {
            setError("Please select a start time");
            setValid(false);
        } else if (getEndTime() == null) {
            setError("Please select an end time");
            setValid(false);
        } else if (getCustomer() == null) {
            setError("Please select a customer");
            setValid(false);
        } else if (getStartTime().equals(getEndTime())) {
            setError("The start time must be prior to the end time");
            setValid(false);
        } else if (getStartTime().isAfter(getEndTime())) {
            setError("The start time must be prior to the end time");
            setValid(false);
        } else if (getStartTime().isBefore(workingHoursStart)
                || getStartTime().isAfter(workingHoursEnd)
                || getEndTime().isBefore(workingHoursStart)
                || getEndTime().isAfter(workingHoursEnd)) {
            setError("Appointments can only be made from 9am to 5pm");
            setValid(false);
        } else if (isIntersectingOtherAppointment) {
            setError("Another appointment is currently scheduled for this time");
            setValid(false);
        } else {
            setValid(true);
        }
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new AppointmentFormSkin(this);
    }

}
