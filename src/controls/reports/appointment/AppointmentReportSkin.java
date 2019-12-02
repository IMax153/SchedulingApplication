/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.reports.appointment;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.beans.InvalidationListener;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * The {@link Skin} for the {@link AppointmentReport} control.
 *
 * @author maxbrown
 */
public class AppointmentReportSkin extends SkinBase<AppointmentReport> {

    private final Label chartLabel;

    public AppointmentReportSkin(AppointmentReport chart) {
        super(chart);

        // Create the root border pane
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("container");

        // Create the appointment report header
        BorderPane header = new BorderPane();
        header.getStyleClass().add("header");

        // Create the header title
        chartLabel = new Label();
        chartLabel.getStyleClass().add("chart-label");
        updateChartLabel(chart.getYearMonth());

        // Add listener to update chart label on changes to the year and month
        InvalidationListener listener = ym -> updateChartLabel(chart.getYearMonth());
        chart.yearMonthProperty().addListener(listener);

        // Add the label to the header
        header.setLeft(chartLabel);

        // Create the container for the header buttons
        HBox buttons = new HBox();
        buttons.getStyleClass().add("buttons");
        buttons.setSpacing(10);

        // Create the appointment report header buttons
        Button backwardButton = new Button("<");
        Button currentButton = new Button("Current Month");
        Button forwardButton = new Button(">");

        // Add the button handlers
        backwardButton.setOnAction(e -> chart.goBackward());
        currentButton.setOnAction(e -> chart.goCurrent());
        forwardButton.setOnAction(e -> chart.goForward());

        // Add buttons to their container
        buttons.getChildren().addAll(backwardButton, currentButton, forwardButton);

        // Add the buttons to the header
        header.setRight(buttons);

        // Add the header to the top of the pane
        pane.setTop(header);

        // Create the bar chart X axis
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Appointment Types");

        // Create the bar chart Y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Appointments");
        yAxis.setTickUnit(1);
        yAxis.setMinorTickVisible(false);

        // Create the bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.getStyleClass().add("chart");
        barChart.setTitle("Appointments Types by Month");
        barChart.setCategoryGap(100);
        barChart.setAnimated(false);
        barChart.setLegendVisible(false);

        // Create a series for the bar chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Bind the chart data to the bar chart series
        series.setData(chart.getData());

        // Add the series to the chart
        barChart.getData().add(series);

        // Add the chart to the center of the pane
        pane.setCenter(barChart);

        // Add the pane to the view
        getChildren().add(pane);
    }

    private void updateChartLabel(YearMonth yearMonth) {
        chartLabel.setText(yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault())));
    }
}
