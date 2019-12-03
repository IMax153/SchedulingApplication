/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.calendar.week;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

    public WeekSkin(Week week) {
        super(week);

        // Create a new grid and expand the grid to fit its container
        grid = new GridPane();
        grid.getStyleClass().add("week");
        grid.setMinSize(0, 0);
        grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        grid.setAlignment(Pos.CENTER);

        // Create a column constraint for the time label column
        ColumnConstraints timeColumnConstraints = new ColumnConstraints();
        timeColumnConstraints.setPercentWidth(5);

        // Add the time label column constraints to the grid
        grid.getColumnConstraints().add(timeColumnConstraints);

        // Create seven columns to represent each day of the week
        ColumnConstraints weekdayColumnConstraints = new ColumnConstraints();
        weekdayColumnConstraints.setPercentWidth(95d / 7d);
        for (int i = 0; i < 7; i++) {
            // Add the weekday column constraints to the grid
            grid.getColumnConstraints().add(weekdayColumnConstraints);
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
        contentGrid.setMinSize(0, 0);
        contentGrid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        contentGrid.setAlignment(Pos.CENTER);

        // Create the content scroller
        ScrollPane content = new ScrollPane(contentGrid);
        content.setFitToWidth(true);
        content.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Create one row to represent the week days
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.ALWAYS);
        contentGrid.getRowConstraints().add(rowConstraints);

        // Create a column to represent the time labels
        ColumnConstraints timeColumnConstraints = new ColumnConstraints();
        timeColumnConstraints.setPercentWidth(5);
        contentGrid.getColumnConstraints().add(timeColumnConstraints);

        // Create seven columns to represent each week day 
        ColumnConstraints weekdayColumnConstraints = new ColumnConstraints();
        weekdayColumnConstraints.setPercentWidth(95d / 7d);
        for (int i = 0; i < 7; i++) {
            contentGrid.getColumnConstraints().add(weekdayColumnConstraints);
        }

        // Get the first day of the week for the view date
        WeekFields weekFields = view.getCalendar().getWeekFields();
        DayOfWeek dayOfWeek = weekFields.getFirstDayOfWeek();

        // Get the day of the month for the first day of the week
        LocalDate date = view.getCalendar().getDate();
        LocalDate dayOfMonth = DateUtils.toFirstDayOfWeek(date, dayOfWeek);

        // Get the start and end times for the time labels
        LocalTime startTime = LocalTime.of(0, 0);
        LocalTime endTime = LocalTime.of(23, 59);
        Duration duration = Duration.ofMinutes(15);

        // Add time header label to the grid
        Label timeHeaderLabel = new Label("Zone: " + DateUtils.DEFAULT_ZONE_ID.getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        timeHeaderLabel.getStyleClass().add("time-header");
        GridPane.setHalignment(timeHeaderLabel, HPos.CENTER);
        grid.add(timeHeaderLabel, 0, 0);

        // Add time labels to the week grid
        VBox timeLabels = new VBox();
        timeLabels.getStyleClass().add("time-labels");

        for (LocalDateTime start = date.atTime(startTime); !start.isAfter(date.atTime(endTime)); start = start.plus(duration)) {
            HBox labelContainer = new HBox();
            labelContainer.getStyleClass().add("label-container");
            labelContainer.setAlignment(Pos.TOP_CENTER);
            labelContainer.setMinSize(0, 40);
            labelContainer.setMaxSize(Double.MAX_VALUE, 40);
            labelContainer.getStyleClass().add("time-label");

            Label timeLabel = new Label(start.format(timeDTF));
            timeLabel.getStyleClass().add("time-label");

            labelContainer.getChildren().add(timeLabel);
            timeLabels.getChildren().add(labelContainer);
        }

        contentGrid.add(timeLabels, 0, 0);

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

            // Create the date label
            Label dateLabel = new Label();
            dateLabel.getStyleClass().add("date-label");
            dateLabel.setText(Integer.toString(dayOfMonth.getDayOfMonth()));
            dateLabel.setAlignment(Pos.CENTER);
            dateLabel.setMinWidth(50);

            // Add the labels to the container
            labelContainer.getChildren().addAll(dayLabel, dateLabel);

            // Add a visual indicator for today's date
            LocalDate now = DateUtils.getToday();
            if (dayOfMonth.equals(now)) {
                dateLabel.getStyleClass().add("today");
            }

            // Add the header to the grid, accounting for time labels
            grid.add(labelContainer, i + 1, 0);

            // Create the week day and add to the grid, accounting for time labels
            WeekDay weekDay = new WeekDay(view, dayOfMonth);
            contentGrid.add(weekDay, i + 1, 0);

            // Increment dates
            dayOfWeek = dayOfWeek.plus(1);
            dayOfMonth = dayOfMonth.plusDays(1);
        }

        // Add the content to the grid
        grid.add(content, 0, 1, 8, 1);
    }

}
