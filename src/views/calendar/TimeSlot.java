/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.calendar;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import models.Appointment;

/**
 *
 * @author mab90
 */
public class TimeSlot {

    /**
     * The pseudo class representing an active time slot.
     */
    private static final PseudoClass ACTIVE_PSEUDO_CLASS = PseudoClass.getPseudoClass("active");

    /**
     * The pseudo class representing selection of the time slot.
     */
    private static final PseudoClass SELECTED_PSEUDO_CLASS = PseudoClass.getPseudoClass("selected");

    /**
     * The pseudo class representing that the time slot is being hovered over.
     */
    private static final PseudoClass HOVERED_PSEUDO_CLASS = PseudoClass.getPseudoClass("hovered");

    /**
     * The GUI representation of the time slot.
     */
    private final HBox root;

    /**
     * A property containing whether or not the time slot is being hovered.
     */
    private final BooleanProperty hovered = new SimpleBooleanProperty();

    /**
     * A property containing whether or not the time slot is selected.
     */
    private final BooleanProperty selected = new SimpleBooleanProperty();

    /**
     * A property containing the start time of the time slot.
     */
    private final ObjectProperty<ZonedDateTime> start = new SimpleObjectProperty();

    /**
     * A property containing the appointment for the time slot, if any.
     */
    private final ObjectProperty<Appointment> appointment = new SimpleObjectProperty<>();

    public TimeSlot(ZonedDateTime start) {
        this.start.set(start);
        this.root = new HBox();

        this.root.getStyleClass().add("time-slot");

        this.appointment.addListener((o, oldValue, newValue) -> {
            if (!isSelected()) {
                root.pseudoClassStateChanged(ACTIVE_PSEUDO_CLASS, newValue != null);
            }
        });

        this.hovered.addListener((o, wasHovered, isHovered) -> {
            root.pseudoClassStateChanged(HOVERED_PSEUDO_CLASS, isHovered);
        });

        this.selected.addListener((o, wasSelected, isSelected) -> {
            if (getAppointment() != null) {
                root.pseudoClassStateChanged(SELECTED_PSEUDO_CLASS, isSelected);
            }
        });
    }

    /**
     * Gets the property containing the {@link TimeSlot#selected} flag.
     *
     * @return The selected property.
     */
    public final BooleanProperty selectedProperty() {
        return selected;
    }

    /**
     * Gets whether or not the {@link TimeSlot} is selected.
     *
     * @return True if the {@link TimeSlot} is selected, otherwise false.
     */
    public final boolean isSelected() {
        return selectedProperty().get();
    }

    /**
     * Sets whether or not the {@link TimeSlot} is selected.
     *
     * @param selected True if the {@link TimeSlot} should be selected,
     * otherwise false.
     */
    public final void setSelected(boolean selected) {
        selectedProperty().set(selected);
    }

    /**
     * Gets the property containing the {@link TimeSlot#hovered} flag.
     *
     * @return The hovered property.
     */
    public final BooleanProperty hoveredProperty() {
        return hovered;
    }

    /**
     * Gets whether or not the {@link TimeSlot} is being hovered.
     *
     * @return True if the {@link TimeSlot} is being hovered, otherwise false.
     */
    public final boolean isHovered() {
        return hoveredProperty().get();
    }

    /**
     * Sets whether or not the {@link TimeSlot} is being hovered.
     *
     * @param hovered True if the {@link TimeSlot} is being hovered, otherwise
     * false.
     */
    public final void setHovered(boolean hovered) {
        hoveredProperty().set(hovered);
    }

    /**
     * Gets the property containing the {@link TimeSlot#start} datetime.
     *
     * @return The start datetime property.
     */
    public final ObjectProperty<ZonedDateTime> startProperty() {
        return start;
    }

    /**
     * Gets the start date of the time slot as a {@link ZonedDateTime}.
     *
     * @return The start date of the time slot as a {@link ZonedDateTime}.
     */
    public final ZonedDateTime getStart() {
        return startProperty().get();
    }

    /**
     * Gets the {@link DayOfWeek} that the time slot starts on.
     *
     * @return The {@link DayOfWeek} that the time slot starts on.
     */
    public final DayOfWeek getDayOfWeek() {
        return getStart().getDayOfWeek();
    }

    /**
     * Gets the property containing the {@link TimeSlot#appointment}.
     *
     * @return The appointment property for the time slot.
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
     * Checks if the {@link TimeSlot} has an appointment.
     *
     * @return True if the {@link TimeSlot} has an appointment, otherwise false.
     */
    public final boolean hasAppointment() {
        return appointmentProperty().get() != null;
    }

    /**
     * Gets the GUI representation of the {@link TimeSlotView}.
     *
     * @return The GUI representation of the {@link TimeSlotView}.
     */
    public final HBox getView() {
        HBox.setHgrow(root, Priority.ALWAYS);
        return root;
    }

}
