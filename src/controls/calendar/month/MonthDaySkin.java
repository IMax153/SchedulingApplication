/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.calendar.month;

import controls.calendar.Calendar;
import controls.dialog.appointment.AppointmentDialog;
import events.AppointmentEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.InvalidationListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Appointment;
import models.Interval;
import utilities.DateUtils;

/**
 * The {@link Skin} for the {@link MonthDay} control.
 *
 * @author maxbrown
 */
public class MonthDaySkin extends SkinBase<MonthDay> {

    private final MonthDay monthDay;

    private final VBox content;

    public MonthDaySkin(MonthDay monthDay) {
        super(monthDay);
        this.monthDay = monthDay;

        // Create the root container for the month day
        VBox root = new VBox();
        root.getStyleClass().add("month-day");

        // Create the month day header
        VBox header = new VBox();
        header.getStyleClass().add("header");
        VBox.setVgrow(header, Priority.NEVER);

        // Create the date label
        Label dateLabel = new Label(Integer.toString(monthDay.getDate().getDayOfMonth()));
        dateLabel.setAlignment(Pos.CENTER);
        dateLabel.setMaxWidth(Double.MAX_VALUE);

        // Add a visual indicator for today's date
        LocalDate now = DateUtils.getToday();
        if (monthDay.getDate().equals(now)) {
            dateLabel.getStyleClass().add("today");
        }

        // Add the date label to the header
        header.getChildren().add(dateLabel);

        // Create the content container for the month day
        content = new VBox();
        content.getStyleClass().add("content");
        content.setSpacing(2);

        // Create a scroll pane to allow the month day content to scroll if necessary
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        VBox.setVgrow(scroller, Priority.ALWAYS);

        updateAppointments();

        // Add the header and the content to the root
        root.getChildren().addAll(header, scroller);

        // Add the root container to the view
        getChildren().add(root);

        InvalidationListener listener = e -> {
            updateAppointments();
        };

        monthDay.getMonth().getCalendar().getAppointments().addListener(listener);
    }

    private void updateAppointments() {
        // Clear the current content
        content.getChildren().clear();

        // Get a reference to the parent calendar
        Calendar calendar = monthDay.getMonth().getCalendar();

        // Iterate through the list of appointments in the calendar
        calendar.getAppointments().forEach(appointment -> {
            // Get the current date for the calendar
            LocalDate date = monthDay.getDate();

            // Create references to the start and end times of the current date
            ZonedDateTime startOfDay = ZonedDateTime.of(date, LocalTime.MIN, DateUtils.DEFAULT_ZONE_ID);
            ZonedDateTime endOfDay = ZonedDateTime.of(date, LocalTime.MAX, DateUtils.DEFAULT_ZONE_ID);

            // Construct an interval representing the current month day
            Interval interval = new Interval(startOfDay, endOfDay);

            // If the appointment intersets the month day, display the appointment
            if (appointment.getInterval().intersects(interval)) {
                content.getChildren().add(createAppointment(appointment));
            }
        });
    }

    private BorderPane createAppointment(Appointment appointment) {
        // Create the root pane for the appointment entry
        BorderPane entry = new BorderPane();
        entry.getStyleClass().add("entry");

        // Create the labels for the appointment
        Label titleLabel = new Label(appointment.getTitle());
        String time = appointment.getInterval().getZonedStartDateTime().format(DateTimeFormatter.ofPattern("hh:mm a"));
        Label timeLabel = new Label(time);

        // Add the labels to the pane
        entry.setLeft(titleLabel);
        entry.setRight(timeLabel);

        // If an entry is clicked, display the appointment dialog with the appropriate
        // appointment
        entry.setOnMouseClicked(e -> {
            e.consume();

            AppointmentDialog dialog = new AppointmentDialog(appointment);

            dialog.showAndWait().ifPresent(result -> entry.fireEvent(new AppointmentEvent(entry, entry, result.getValue(), result.getKey())));
        });

        return entry;
    }

}
