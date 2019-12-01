/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.calendar.month;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import utilities.DateUtils;

/**
 * The {@link Skin} for the {@link Month} control.
 *
 * @author maxbrown
 */
public class MonthSkin extends SkinBase<Month> {

    private final GridPane grid;

    public MonthSkin(Month month) {
        super(month);

        // Create a new grid and expand the grid to fit its container
        grid = new GridPane();
        grid.getStyleClass().add("month");
        grid.setMinSize(0, 0);
        grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        grid.setAlignment(Pos.CENTER);

        // Create seven columns to represent each day of the week
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100d / 7d);
        for (int i = 0; i < 7; i++) {
            grid.getColumnConstraints().add(columnConstraints);
        }

        // Create the header row that will contain the date labels
        RowConstraints headerConstraints = new RowConstraints();
        grid.getRowConstraints().add(headerConstraints);

        // Create six rows to represent the weeks of the month, including any
        // overlap with months before and after the current month
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100 / 6);
        for (int i = 0; i < 6; i++) {
            grid.getRowConstraints().add(rowConstraints);
        }

        // Update the view
        updateView();

        // Add the grid to the view
        getChildren().add(grid);

        // Update the view when the calendar date changes
        month.getCalendar().dateProperty().addListener(e -> updateView());
    }

    private void updateView() {
        // Clear the grid
        grid.getChildren().clear();

        // Get a reference to the month view
        Month view = getSkinnable();

        // Get the first day of the week for the view date
        WeekFields weekFields = view.getCalendar().getWeekFields();
        DayOfWeek dayOfWeek = weekFields.getFirstDayOfWeek();

        // For each day of the week
        for (int i = 0; i < 7; i++) {
            // Create labels for the days
            Label label = new Label(dayOfWeek.getDisplayName(TextStyle.SHORT_STANDALONE, Locale.getDefault()).toUpperCase());
            label.getStyleClass().add("week-day");
            label.setAlignment(Pos.CENTER);
            label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            GridPane.setHgrow(label, Priority.ALWAYS);

            // Add the label to the first grid row
            grid.add(label, i, 0);

            // Increment the day of the week
            dayOfWeek = dayOfWeek.plus(1);

            // Get a reference to the first day of the first week in the month
            // Note that this may not always be the first day of the month
            LocalDate date = view.getCalendar().getDate().with(TemporalAdjusters.firstDayOfMonth());
            date = DateUtils.toFirstDayOfWeek(date, view.getCalendar().getFirstDayOfWeek());

            for (int week = 0; week < 6; week++) {
                for (int day = 0; day < 7; day++) {
                    MonthDay monthDay = new MonthDay(view, date);

                    // Add month day to the grid
                    grid.add(monthDay, day, week + 1);

                    // Increment date
                    date = date.plusDays(1);
                }
            }
        }
    }

}
