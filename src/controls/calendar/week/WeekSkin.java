/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.calendar.week;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import utilities.DateUtils;

/**
 * The {@link Skin} for the {@link Week} control.
 *
 * @author maxbrown
 */
public class WeekSkin extends SkinBase<Week> {

    private final GridPane grid;

    public WeekSkin(Week week) {
        super(week);

        // Create a new grid and expand the grid to fit its container
        grid = new GridPane();
        grid.getStyleClass().add("week");
        grid.setMinSize(0, 0);
        grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        grid.setAlignment(Pos.CENTER);

        // Create seven columns to represent each day of the week
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100d / 7d);
        for (int i = 0; i < 7; i++) {
            // Add the column constraints to the grid
            grid.getColumnConstraints().add(columnConstraints);
        }

        // Create header row constraints
        RowConstraints headerConstraints = new RowConstraints();
        headerConstraints.setVgrow(Priority.NEVER);

        // Create content row constraints
        RowConstraints contentConstraints = new RowConstraints();
        contentConstraints.setVgrow(Priority.ALWAYS);

        // Add row constraints to the grid
        grid.getRowConstraints().addAll(headerConstraints, contentConstraints);

        // Update the view
        updateView();

        // Add the grid to the view
        getChildren().add(grid);

        // Update the view when the calendar date changes
        week.getCalendar().dateProperty().addListener(e -> updateView());
    }

    private void updateView() {
        // Clear the grid
        grid.getChildren().clear();

        // Get a reference to the view
        Week view = getSkinnable();

        // Create the content grid view
        GridPane contentGrid = new GridPane();
        contentGrid.getStyleClass().add("content");

        // Create the content scroller
        ScrollPane content = new ScrollPane(contentGrid);
        content.setFitToHeight(true);
        content.setFitToWidth(true);
        content.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Create one row to represent the week days
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        contentGrid.getRowConstraints().add(rowConstraints);

        // Create seven columns to represent each week day 
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100d / 7d);
        for (int i = 0; i < 7; i++) {
            contentGrid.getColumnConstraints().add(columnConstraints);
        }

        // Get the first day of the week for the view date
        WeekFields weekFields = view.getCalendar().getWeekFields();
        DayOfWeek dayOfWeek = weekFields.getFirstDayOfWeek();

        // Get the day of the month for the first day of the week
        LocalDate date = view.getCalendar().getDate();
        LocalDate dayOfMonth = DateUtils.toFirstDayOfWeek(date, dayOfWeek);

        for (int i = 0; i < 7; i++) {
            // Create a container for the labels
            VBox labelContainer = new VBox();
            labelContainer.getStyleClass().add("label-container");
            labelContainer.setAlignment(Pos.CENTER);

            // Create the day label
            Label dayLabel = new Label();
            dayLabel.getStyleClass().add("day-label");
            dayLabel.setText(dayOfWeek.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault()).toUpperCase());
            dayLabel.setAlignment(Pos.CENTER);
            GridPane.setHgrow(dayLabel, Priority.ALWAYS);

            // Create the date label
            Label dateLabel = new Label();
            dateLabel.getStyleClass().add("date-label");
            dateLabel.setText(Integer.toString(dayOfMonth.getDayOfMonth()));
            dateLabel.setAlignment(Pos.CENTER);
            GridPane.setHgrow(dateLabel, Priority.ALWAYS);

            // Add the labels to the container
            labelContainer.getChildren().addAll(dayLabel, dateLabel);

            // Add a visual indicator for today's date
            LocalDate now = DateUtils.getToday();
            if (dayOfMonth.equals(now)) {
                dateLabel.getStyleClass().add("today");
            }

            // Add the header to the grid
            grid.add(labelContainer, i, 0);

            // Create the week day and add to the grid
            WeekDay weekDay = new WeekDay(view, dayOfMonth);
            contentGrid.add(weekDay, i, 0);

            // Increment dates
            dayOfWeek = dayOfWeek.plus(1);
            dayOfMonth = dayOfMonth.plusDays(1);
        }

        // Add the content to the grid
        grid.add(content, 0, 1, 7, 1);
    }

}
