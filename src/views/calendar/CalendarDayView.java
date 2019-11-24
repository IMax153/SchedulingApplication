/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.calendar;

import java.time.LocalDate;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.Appointment;

/**
 * Handles rendering individual days for the {@link CalendarViewController}.
 *
 * @author mab90
 */
public class CalendarDayView {

    /**
     * The date representing a calendar day.
     */
    private final LocalDate date;

    /**
     * The list of appointments for the calendar date.
     */
    private final List<Appointment> appointments;

    public CalendarDayView(LocalDate date, List<Appointment> appointments) {
        this.date = date;
        this.appointments = appointments;
    }

    /**
     * Gets the GUI representation of the {@link CalendarDayView}.
     *
     * @return The GUI node.
     */
    public Node getView() {
        VBox vBox = new VBox();
        vBox.setMaxHeight(Double.MAX_VALUE);
        vBox.setMaxWidth(Double.MAX_VALUE);
        vBox.setStyle("-fx-background-color: gray; -fx-background-radius: 10; -fx-border-color: c0c0c0; -fx-border-radius: 10;");

        Label dayOfMonthLabel = new Label(Integer.toString(date.getDayOfMonth()));
        dayOfMonthLabel.setPadding(new Insets(5));
        GridPane.setHalignment(dayOfMonthLabel, HPos.CENTER);
        GridPane.setValignment(dayOfMonthLabel, VPos.TOP);

        vBox.setOnMouseClicked(e -> {
            System.out.println(appointments.toString());
        });

        vBox.getChildren().add(dayOfMonthLabel);

        return vBox;
    }

}
