/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.calendar;

import controls.dialog.appointment.AppointmentDialog;
import events.AppointmentEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * The {@link Skin} for the {@link Calendar}.
 *
 * @author maxbrown
 */
public class CalendarSkin extends SkinBase<Calendar> {

    private final Label dateLabel;

    public CalendarSkin(Calendar calendar) {
        super(calendar);

        // Create the calendar container
        BorderPane root = new BorderPane();
        root.getStyleClass().add("calendar");

        // Create the calendar header
        BorderPane header = new BorderPane();
        header.getStyleClass().add("header");

        // Create the navigation container
        HBox navigation = new HBox();
        navigation.getStyleClass().add("navigation");
        navigation.setSpacing(10);

        // Create the navigation and new appointment buttons
        Button todayButton = new Button("Today");
        Button backwardButton = new Button("<");
        Button forwardButton = new Button(">");
        Button addAppointmentButton = new Button("Add Appointment");

        // Add the navigation and new appointment button handlers
        todayButton.setOnAction(e -> calendar.goToday());
        backwardButton.setOnAction(e -> calendar.goBackward());
        forwardButton.setOnAction(e -> calendar.goForward());
        addAppointmentButton.setOnAction(e -> {
            e.consume();

            AppointmentDialog dialog = new AppointmentDialog();

            dialog.showAndWait().ifPresent(result
                    -> addAppointmentButton.fireEvent(
                            new AppointmentEvent(addAppointmentButton, addAppointmentButton, result.getValue(), result.getKey())
                    )
            );
        });

        // Add the navigation buttons to their container
        navigation.getChildren().addAll(todayButton, backwardButton, forwardButton, addAppointmentButton);

        // Create the view selector container
        HBox viewSelector = new HBox();
        viewSelector.getStyleClass().add("view-selector");

        viewSelector.setAlignment(Pos.CENTER);
        viewSelector.setSpacing(10);

        // Create view selector buttons
        Button weekToggle = new Button("Week");
        Button monthToggle = new Button("Month");

        // Add the view selector button handlers
        weekToggle.setOnAction(e -> calendar.setViewType(Calendar.ViewType.WEEK));
        monthToggle.setOnAction(e -> calendar.setViewType(Calendar.ViewType.MONTH));

        // Add the view selector buttons to their container
        viewSelector.getChildren().addAll(weekToggle, monthToggle);

        // Create the date label for the calendar
        dateLabel = new Label();
        dateLabel.getStyleClass().add("date-label");
        updateDateLabel(calendar.getDate());

        // Bind the calendar date to the date label 
        calendar.dateProperty().addListener(e -> updateDateLabel(calendar.getDate()));

        // Add the header to the page
        header.setLeft(navigation);
        header.setCenter(viewSelector);
        header.setRight(dateLabel);
        root.setTop(header);

        // Add the selected view to the page
        root.setCenter(calendar.getSelectedView());

        // Add the root to the view
        getChildren().add(root);

        // Listen for changes to the view
        calendar.selectedViewProperty().addListener((obs, oldView, newView) -> root.setCenter(calendar.getSelectedView()));

        // Display an alert to the user if an appointment is within 15 minutes
        Platform.runLater(()
                -> calendar.upcomingAppointments().forEach(appointment -> {
                    StringBuilder sb = new StringBuilder();

                    sb.append("Appointment");
                    sb.append(System.getProperty("line.separator"));

                    sb.append("Title: ");
                    sb.append(appointment.getTitle());
                    sb.append(System.getProperty("line.separator"));

                    sb.append("Type: ");
                    sb.append(appointment.getType());
                    sb.append(System.getProperty("line.separator"));

                    sb.append("With: ");
                    sb.append(appointment.getCustomer().getName());

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Upcoming Appointment");
                    alert.setHeaderText("You have the following appointment occurring within the next 15 minutes:");
                    alert.setContentText(sb.toString());

                    alert.showAndWait();
                })
        );
    }

    private void updateDateLabel(LocalDate date) {
        dateLabel.setText(date.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
    }

}
