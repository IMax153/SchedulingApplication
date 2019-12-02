/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.reports.consultant;

import dao.AppointmentDao;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import models.Appointment;
import models.User;

/**
 * The {@link Control} for displaying a tabular view of a consultant's schedule.
 *
 * @author maxbrown
 */
public class ConsultantSchedule extends Control {

    private final AppointmentDao appointmentDao = new AppointmentDao();

    private final User consultant;

    public ConsultantSchedule(User consultant) {
        this.consultant = consultant;

        String stylesheet = getClass().getResource("consultant-schedule.css").toExternalForm();
        getStylesheets().add(stylesheet);

        appointmentDao.findAllForUser(consultant).stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(appointment -> getConsultantAppointments().add(appointment));
    }

    /**
     * Returns the value of {@link ConsultantSchedule#consultant}.
     *
     * @return The consultant.
     */
    public final User getConsultant() {
        return consultant;
    }

    private final ObservableList<Appointment> consultantAppointments = FXCollections.observableArrayList();

    /**
     * Returns the list of {@link Appointment}s for the
     * {@link ConsultantSchedule#consultant}.
     *
     * @return The list of appointments.
     */
    public final ObservableList<Appointment> getConsultantAppointments() {
        return consultantAppointments;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new ConsultantScheduleSkin(this);
    }
}
