/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.reports.customer;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;

/**
 * The {@link Skin} for the {@link CustomerReport} control.
 *
 * @author maxbrown
 */
public class CustomerReportSkin extends SkinBase<CustomerReport> {

    public CustomerReportSkin(CustomerReport report) {
        super(report);

        // Create the root border pane
        BorderPane pane = new BorderPane();
        pane.getStyleClass().add("container");

        // Create the customer report header
        BorderPane header = new BorderPane();
        header.getStyleClass().add("header");

        // Create the header title
        Label chartLabel = new Label("Customer Report");
        chartLabel.getStyleClass().add("chart-label");

        // Add the label to the header
        header.setLeft(chartLabel);

        // Add the header to the top of the pane
        pane.setTop(header);

        // Create the bar chart X axis
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Cities");

        // Create the bar chart Y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of Customers");
        yAxis.setTickUnit(1);
        yAxis.setMinorTickVisible(false);

        // Create the bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.getStyleClass().add("chart");
        barChart.setTitle("Number of Customers Per City");
        barChart.setCategoryGap(100);
        barChart.setAnimated(false);
        barChart.setLegendVisible(false);

        // Create a series for the bar chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Bind the chart data to the bar chart series
        series.setData(report.getData());

        // Add the series to the chart
        barChart.getData().add(series);

        // Add the chart to the center of the pane
        pane.setCenter(barChart);

        // Add the pane to the view
        getChildren().add(pane);
    }

}
