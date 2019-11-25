/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a time slot on the calendar within the application.
 *
 * @author mab90
 */
public class CalendarTimeSlot {

    /**
     * The start time of the time slot.
     */
    private final ZonedDateTime start;

    /**
     * The appointment contained within the time slot, if any.
     */
    private ObjectProperty<Appointment> appointment = new SimpleObjectProperty<>();

    public CalendarTimeSlot(ZonedDateTime start) {
        this.start = start;
    }

    /**
     * Gets the start date of the time slot as a {@link ZonedDateTime}.
     *
     * @return The start date of the time slot as a {@link ZonedDateTime}.
     */
    public final ZonedDateTime getStart() {
        return start;
    }

    /**
     * Gets the start date of the time slot as a {@link LocalTime};
     *
     * @return The start date of the time slot as a {@link LocalTime}.
     */
    public final LocalTime getStartTime() {
        return start.toLocalTime();
    }

    /**
     * Gets the {@link DayOfWeek} that the time slot starts on.
     *
     * @return The {@link DayOfWeek} that the time slot starts on.
     */
    public final DayOfWeek getDayOfWeek() {
        return start.getDayOfWeek();
    }

    /**
     * Gets the appointment property for the time slot.
     *
     * @return the appointment property for the time slot.
     */
    public final ObjectProperty<Appointment> appointmentProperty() {
        return appointment;
    }

    /**
     * Gets the appointment for the time slot.
     *
     * @return The appointment for the time slot.
     */
    public final Appointment getAppointment() {
        return appointmentProperty().get();
    }

    /**
     * Sets the appointment for the time slot.
     *
     * @param appointment The appointment for the time slot.
     */
    public final void setAppointment(Appointment appointment) {
        appointmentProperty().set(appointment);
    }

    /**
     * Checks if the {@link CalendarTimeSlot} has an appointment.
     *
     * @return True if the {@link CalendarTimeSlot} has an appointment,
     * otherwise false.
     */
    public final boolean hasAppointment() {
        return appointmentProperty().get() != null;
    }

}
