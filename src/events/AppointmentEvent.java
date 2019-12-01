/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import controls.form.appointment.AppointmentForm;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import models.Appointment;

import static java.util.Objects.requireNonNull;

/**
 * An {@link Event} that signals the {@link SchedulingApplication} to navigate
 * to a new view.
 *
 * @author maxbrown
 */
public class AppointmentEvent extends Event {

    /**
     * The default {@link AppointmentEvent} type.
     */
    public static final EventType<AppointmentEvent> ALL_APPOINTMENT = new EventType<>(AppointmentEvent.ANY, "ALL_APPOINTMENT");

    /**
     * The {@link AppointmentEvent} type that signals the creation of a new
     * {@link Appointment}.
     */
    public static final EventType<AppointmentEvent> NEW_APPOINTMENT = new EventType<>(AppointmentEvent.ALL_APPOINTMENT, "NEW_APPOINTMENT");

    /**
     * The {@link AppointmentEvent} type that signals the modification of an
     * existing {@link Appointment}.
     */
    public static final EventType<AppointmentEvent> EDIT_APPOINTMENT = new EventType<>(AppointmentEvent.ALL_APPOINTMENT, "EDIT_APPOINTMENT");

    /**
     * The {@link AppointmentEvent} type that signals the deletion of an
     * existing {@link Appointment}.
     */
    public static final EventType<AppointmentEvent> DELETE_APPOINTMENT = new EventType<>(AppointmentEvent.ALL_APPOINTMENT, "DELETE_APPOINTMENT");

    private final Appointment appointment;

    public AppointmentEvent(Object source, EventTarget target, Appointment appointment, AppointmentForm.Mode mode) {
        super(source, target, mode.equals(AppointmentForm.Mode.NEW) ? NEW_APPOINTMENT
                : mode.equals(AppointmentForm.Mode.EDIT) ? EDIT_APPOINTMENT
                : DELETE_APPOINTMENT
        );
        this.appointment = requireNonNull(appointment);
    }

    /**
     * Returns the value of {@link AppointmentEvent#appointment}.
     *
     * @return The appointment.
     */
    public Appointment getAppointment() {
        return appointment;
    }

}
