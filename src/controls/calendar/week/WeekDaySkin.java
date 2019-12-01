/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.calendar.week;

import controls.calendar.Calendar;
import controls.dialog.appointment.AppointmentDialog;
import events.AppointmentEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.InvalidationListener;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import models.Appointment;
import utilities.DateUtils;

/**
 * The {@link Skin} for the {@link WeekDay} control.
 *
 * @author maxbrown
 */
public class WeekDaySkin extends SkinBase<WeekDay> {

    private final LocalTime startTime = LocalTime.of(0, 0);

    private final LocalTime endTime = LocalTime.of(23, 59);

    private final Duration duration = Duration.ofMinutes(15);

    private final VBox root;

    private final WeekDay weekDay;

    private final List<WeekDayTimeSlot> timeSlots;

    public WeekDaySkin(WeekDay weekDay) {
        super(weekDay);
        this.weekDay = weekDay;
        this.timeSlots = new ArrayList<>();

        // Create the root for the week day
        this.root = new VBox();
        this.root.getStyleClass().add("week-day");

        // Update the view
        updateView();

        // Add the root to the view
        getChildren().add(root);

        // Update the view on changes to the calendar appointments
        InvalidationListener listener = e -> updateView();
        weekDay.getWeek().getCalendar().getAppointments().addListener(listener);
    }

    private void updateView() {
        // Clear the current content
        root.getChildren().clear();
        timeSlots.clear();

        // Get the current calendar date
        LocalDate date = weekDay.getDate();

        for (LocalDateTime start = date.atTime(startTime); !start.isAfter(date.atTime(endTime)); start = start.plus(duration)) {
            WeekDayTimeSlot timeSlot = new WeekDayTimeSlot(start, duration);

            timeSlots.add(timeSlot);

            Region timeSlotView = timeSlot.getView();

            root.getChildren().add(timeSlotView);
        }

        // Update the appointments
        updateAppointments();
    }

    private void updateAppointments() {
        // Get a reference to the parent calendar
        Calendar calendar = weekDay.getWeek().getCalendar();

        // Iterate through the list of appointments in the calendar
        calendar.getAppointments().forEach(appointment -> {
            // Add the appointment to appropriate time slots 
            addToContainingTimeSlots(appointment);
        });
    }

    private void addToContainingTimeSlots(Appointment appointment) {
        boolean wasLabeled = false;

        // Filter time slots that are not contained within the appointment
        List<WeekDayTimeSlot> filteredTimeSlots = timeSlots.stream().filter(
                timeSlot -> appointment.getInterval().contains(
                        DateUtils.toZonedDateTime(timeSlot.getDateTime())
                )
        ).collect(Collectors.toList());

        for (WeekDayTimeSlot timeSlot : filteredTimeSlots) {
            // If we have not yet labeled the appointment, set the label
            if (!wasLabeled) {
                timeSlot.setAppointmentLabel(appointment);
                wasLabeled = true;
            }

            // Get a reference to the time slot view
            HBox view = timeSlot.getView();
            view.getStyleClass().add("entry");

            // If a time slot with an appointment is clicked, display the appointment dialog with the appropriate
            // appointment
            view.setOnMouseClicked(e -> {
                e.consume();

                AppointmentDialog dialog = new AppointmentDialog(appointment);

                dialog.showAndWait().ifPresent(result
                        -> view.fireEvent(new AppointmentEvent(view, view, result.getValue(), result.getKey())));
            });
        };
    }

    /**
     * Class representing the individual time slots for the {@link WeekDays}
     * view.
     */
    class WeekDayTimeSlot {

        private final LocalDateTime dateTime;

        private final Duration duration;

        private final HBox view;

        public WeekDayTimeSlot(LocalDateTime dateTime, Duration duration) {
            this.dateTime = dateTime;
            this.duration = duration;

            // Create the time slot view
            view = new HBox();
            view.setMinSize(0, 40);
            view.setMaxSize(Double.MAX_VALUE, 40);
            view.getStyleClass().add("time-slot");
        }

        /**
         * Returns the {@link WeekDayTimeSlot#dateTime}.
         *
         * @return The date and time.
         */
        public LocalDateTime getDateTime() {
            return dateTime;
        }

        /**
         * Returns the {@link WeekDayTimeSlot#duration}.
         *
         * @return The duration.
         */
        public Duration getDuration() {
            return duration;
        }

        /**
         * /**
         * Returns the {@link WeekDayTimeSlot#view}.
         *
         * @return The region view.
         */
        public HBox getView() {
            return view;
        }

        /**
         * Creates a label for the {@link WeekDayTimeSlot}
         *
         * @param appointment The appointment to use to create the label.
         */
        public void setAppointmentLabel(Appointment appointment) {
            // Create the root pane for the appointment entry
            BorderPane entryLabel = new BorderPane();
            entryLabel.getStyleClass().add("entry-label");
            HBox.setHgrow(entryLabel, Priority.ALWAYS);

            // Create the title label for the appointment
            Label titleLabel = new Label(appointment.getTitle());
            titleLabel.getStyleClass().add("title-label");
            titleLabel.setWrapText(true);

            // Create the time label for the appointment
            Label timeLabel = new Label(
                    appointment.getInterval().getZonedStartDateTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
            );
            timeLabel.getStyleClass().add("time-label");

            // Add the labels to the pane
            entryLabel.setLeft(titleLabel);
            entryLabel.setRight(timeLabel);

            // Add the pane to the view
            view.getChildren().add(entryLabel);
        }
    }

}
